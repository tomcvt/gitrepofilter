package com.tomcvt.gitrepofilter.filtermodule;

import java.util.List;

public record RepositoryWithBranches(
    String name,
    String ownerLogin,
    List<Branch> branches
) {
    
}
