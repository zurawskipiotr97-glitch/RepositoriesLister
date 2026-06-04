package pl.zurawskipiotr97glitch.repositorieslister;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
class GithubClient {

    private final RestClient restClient;

    GithubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    List<GithubRepository> getRepositories(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    record GithubRepository(
            String name,
            boolean fork,
            GithubOwner owner
    ) {
    }

    record GithubOwner(
            String login
    ) {
    }
}