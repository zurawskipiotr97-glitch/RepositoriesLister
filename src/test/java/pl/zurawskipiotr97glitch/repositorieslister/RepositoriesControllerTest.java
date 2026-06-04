package pl.zurawskipiotr97glitch.repositorieslister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
        @ConfigureWireMock(baseUrlProperties = "github.api.url")
)
class RepositoriesControllerTest {

    private final RestClient restClient;

    RepositoriesControllerTest(@Value("${local.server.port}") int port) {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldReturnNonForkRepositoriesWithBranches() {
        stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                    [
                      {
                        "name": "repo-one",
                        "fork": false,
                        "owner": {
                          "login": "testuser"
                        }
                      },
                      {
                        "name": "forked-repo",
                        "fork": true,
                        "owner": {
                          "login": "testuser"
                        }
                      }
                    ]
                    """)));

        stubFor(get(urlEqualTo("/repos/testuser/repo-one/branches"))
                .willReturn(okJson("""
                    [
                      {
                        "name": "main",
                        "commit": {
                          "sha": "abc123"
                        }
                      }
                    ]
                    """)));

        ResponseEntity<String> response = restClient.get()
                .uri("/testuser")
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("repo-one");
        assertThat(response.getBody()).contains("testuser");
        assertThat(response.getBody()).contains("main");
        assertThat(response.getBody()).contains("abc123");
        assertThat(response.getBody()).doesNotContain("forked-repo");
    }

    @Test
    void shouldReturn404WhenGithubUserDoesNotExist() {
        stubFor(get(urlEqualTo("/users/unknown/repos"))
                .willReturn(notFound()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "message": "Not Found",
                              "documentation_url": "https://docs.github.com/rest",
                              "status": "404"
                            }
                            """)));

        assertThatThrownBy(() ->
                restClient.get()
                        .uri("/unknown")
                        .retrieve()
                        .toEntity(String.class)
        )
                .isInstanceOf(org.springframework.web.client.HttpClientErrorException.NotFound.class)
                .hasMessageContaining("Not Found");
    }
}