package pl.zurawskipiotr97glitch.repositorieslister;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RepositoriesService {

    private final GithubClient githubClient;

    RepositoriesService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    List<Repository> getRepositories(String username) {
        return githubClient.getRepositories(username)
                .stream()
                .filter(repository -> !repository.fork())
                .map(repository -> new Repository(
                        repository.name(),
                        repository.owner().login(),
                        List.of()
                ))
                .toList();
    }
}