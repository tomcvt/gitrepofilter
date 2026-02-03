package com.tomcvt.gitrepofilter.filtermodule;

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
    public List<Repository> filterRepositories(@PathVariable String username) {

    }
}