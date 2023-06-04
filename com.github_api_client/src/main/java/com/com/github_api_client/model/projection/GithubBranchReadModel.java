package com.com.github_api_client.model.projection;

import com.com.github_api_client.model.GithubBranch;

public class GithubBranchReadModel {

    private String branchName;
    private String lastCommitSHA;

    public GithubBranchReadModel() {
    }

    public GithubBranchReadModel(GithubBranch githubBranch) {
        this.branchName = githubBranch.getName();
        this.lastCommitSHA = githubBranch.getCommit().getSha();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastCommitSHA() {
        return lastCommitSHA;
    }

    public void setLastCommitSHA(String lastCommitSHA) {
        this.lastCommitSHA = lastCommitSHA;
    }
}
