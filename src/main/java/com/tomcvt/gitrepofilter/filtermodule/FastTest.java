package com.tomcvt.gitrepofilter.filtermodule;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class FastTest implements ApplicationListener<ApplicationReadyEvent> {

    FilterService filterService;

    public FastTest(FilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("FastTest triggered");
        var repos = filterService.filterRepositories("tomcvt");
        System.out.println("Filtered repositories: " + repos);
    }
}
