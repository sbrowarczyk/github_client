package com.com.github_api_client.model.projection;

import com.com.github_api_client.model.GithubBranch;

public class GithubBranchReadModel {

    private String branchName;
    private String lastCommitSHA;

     public GithubBranchReadModel(GithubBranch githubBranch) {
        this.branchName = githubBranch.getName();
        this.lastCommitSHA = githubBranch.getCommit().getSha();
    }

    String getBranchName() {
        return branchName;
    }

    void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    String getLastCommitSHA() {
        return lastCommitSHA;
    }

    void setLastCommitSHA(String lastCommitSHA) {
        this.lastCommitSHA = lastCommitSHA;
    }
}
