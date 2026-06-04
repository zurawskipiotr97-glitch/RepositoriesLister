package pl.zurawskipiotr97glitch.repositorieslister;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class RepositoriesListerApplication {

    static void main(String[] args) {
        SpringApplication.run(RepositoriesListerApplication.class, args);
    }

    @Bean
    RestClient restClient(@Value("${github.api.url:https://api.github.com}") String githubApiUrl) {
        return RestClient.builder()
                .baseUrl(githubApiUrl)
                .build();
    }
}