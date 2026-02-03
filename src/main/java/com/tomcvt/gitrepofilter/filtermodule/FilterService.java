package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

@Service
public class FilterService {
    private final GitHubClient gitHubClient;

    public FilterService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<RepositoryWithBranches> filterRepositories(String username) {
        List<RepositoryResponse> repositories = gitHubClient.getUserRepositories(username);
        if (repositories == null) {
            throw new UserNotFoundException("User not found: " + username);
        }
        List<RepositoryResponse> filteredRepositories = repositories.stream()
                .filter(repo -> !repo.fork())
                .toList();
    }
}
