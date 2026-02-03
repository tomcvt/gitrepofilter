package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GitHubClient {

    RestClient restClient;

    public GitHubClient() {
        this.restClient = RestClient.create();
    }

    
    public List<RepositoryResponse> fetchUserRepositories(String username) {
        return restClient.get()
            .uri("https://api.github.com/users/" + username + "/repos")
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
            .uri("https://api.github.com/repos/" + owner + "/" + repoName + "/branches")
            .retrieve()
            .body(new ParameterizedTypeReference<List<BranchResponse>>() {});
    }
}
