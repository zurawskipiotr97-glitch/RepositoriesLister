package pl.zurawskipiotr97glitch.repositorieslister;

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
    RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://api.github.com")
                .build();
    }

}
