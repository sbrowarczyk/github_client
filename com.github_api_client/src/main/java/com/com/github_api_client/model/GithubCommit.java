package com.com.github_api_client.model;

public class GithubCommit {

    private String sha;

    public GithubCommit() {
    }

    public GithubCommit(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    void setSha(String sha) {
        this.sha = sha;
    }


}
