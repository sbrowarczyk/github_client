package com.com.github_api_client.model;

public class GithubOwner {

    private String login;

    public GithubOwner() {
    }

    public GithubOwner(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        this.login = login;
    }
}
