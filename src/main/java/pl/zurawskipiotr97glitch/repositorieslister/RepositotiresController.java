package pl.zurawskipiotr97glitch.repositorieslister;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepositotiresController {

    private final RepositoriesService repositoriesService;

    public RepositotiresController(RepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @GetMapping("/{username}")
    List<Repository> getRepositories(@PathVariable String username) {
        return repositoriesService.getRepositories(username);
    }
}
