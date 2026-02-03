package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class ApiController {
    private final FilterService filterService;

    public ApiController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/filter-repositories/{username}")
    public List<RepositoryWithBranches> filterRepositories(@PathVariable String username) {
        return filterService.filterRepositories(username);
    }
}