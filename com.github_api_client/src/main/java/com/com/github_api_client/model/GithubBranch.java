package com.com.github_api_client.model;

public class GithubBranch {

    private String name;
    private GithubCommit commit;

    public GithubBranch() {
    }

    public GithubBranch(String name, GithubCommit commit) {
        this.name = name;
        this.commit = commit;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public GithubCommit getCommit() {
        return commit;
    }

    void setCommit(GithubCommit commit) {
        this.commit = commit;
    }
}
