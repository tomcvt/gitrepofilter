package com.tomcvt.gitrepofilter.filtermodule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FilterService {
    private final GitHubClient gitHubClient;

    public FilterService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<RepositoryWithBranches> filterRepositories(String username) {
        List<RepositoryResponse> repositories = gitHubClient.fetchUserRepositories(username);
        if (repositories == null) {
            throw new UserNotFoundException("User not found: " + username);
        }
        List<RepositoryResponse> filteredRepositories = repositories.stream()
                .filter(repo -> !repo.fork())
                .toList();

        //System.out.println("Filtered repositories (not forks): " + filteredRepositories);
            
        List<RepositoryWithBranches> result = new ArrayList<>();
        
        for (RepositoryResponse repo : filteredRepositories) {
            List<Branch> branches = gitHubClient.fetchRepositoryBranches(repo.owner().login(), repo.name())
                .stream()
                .map(branchResp -> new Branch(
                    branchResp.name(),
                    branchResp.commit().sha()
                ))
                .toList();
            result.add(new RepositoryWithBranches(
                repo.name(),
                repo.owner().login(),
                branches
            ));
        }

        return result;
    }
}
