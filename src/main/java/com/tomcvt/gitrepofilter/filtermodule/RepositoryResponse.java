package com.tomcvt.gitrepofilter.filtermodule;

public record RepositoryResponse(
    String name,
    String url,
    Owner owner,
    Boolean fork
) {
    
}
