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
                .map(repository -> {
                    List<Branch> branches = githubClient
                            .getBranches(repository.owner().login(), repository.name())
                            .stream()
                            .map(branch -> new Branch(
                                    branch.name(),
                                    branch.commit().sha()
                            ))
                            .toList();

                    return new Repository(
                            repository.name(),
                            repository.owner().login(),
                            branches
                    );
                })
                .toList();
    }
}