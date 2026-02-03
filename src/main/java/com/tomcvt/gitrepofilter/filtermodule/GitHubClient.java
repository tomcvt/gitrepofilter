package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GitHubClient {

    private final RestClient restClient;
    private final String baseUrl;

    public GitHubClient(
        @Value("${gitrepo.filter.github.api.base-url}") String baseUrl
    ) {
        this.restClient = RestClient.create();
        this.baseUrl = baseUrl;
    }
    
    public List<RepositoryResponse> fetchUserRepositories(String username) {
        return restClient.get()
            .uri(baseUrl + "/users/" + username + "/repos")
            .retrieve().onStatus(
                status -> status.value() == 404,
                (request, response) -> {
                    throw new UserNotFoundException("User not found");
                }
            )
            .body(new ParameterizedTypeReference<List<RepositoryResponse>>() {});
            
    }

    public List<BranchResponse> fetchRepositoryBranches(String owner, String repoName) {
        return restClient.get()
            .uri(baseUrl + "/repos/" + owner + "/" + repoName + "/branches")
            .retrieve()
            .body(new ParameterizedTypeReference<List<BranchResponse>>() {});
    }
}
