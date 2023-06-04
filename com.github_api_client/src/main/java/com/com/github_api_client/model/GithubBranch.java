package com.com.github_api_client.model;

public class GithubBranch {

    private String name;
    private GithubCommit commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GithubCommit getCommit() {
        return commit;
    }

    public void setCommit(GithubCommit commit) {
        this.commit = commit;
    }
}