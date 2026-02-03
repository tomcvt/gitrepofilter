package com.tomcvt.gitrepofilter.filtermodule;

public record BranchResponse(
    String name,
    Commit commit
) {
    
}
