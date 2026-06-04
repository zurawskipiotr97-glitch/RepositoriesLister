package pl.zurawskipiotr97glitch.repositorieslister;

import java.util.List;

public record Repository(
        String repositoryName,
        String ownerLogin,
        List<Branch> branches
) {
}

