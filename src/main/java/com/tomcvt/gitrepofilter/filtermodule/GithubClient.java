package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class GithubClient {

    RestClient restClient;

    public GithubClient() {
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

    
}
