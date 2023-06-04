package com.com.github_api_client.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GithubRepository {

    private String name;
    private GithubOwner owner;
    private boolean fork;
    private List<GithubBranch> branches;
    @JsonProperty("branches_url")
    private String branchesURL;

    public GithubRepository() {
    }

    public GithubRepository(String name, GithubOwner owner, boolean fork, List<GithubBranch> branches, String branchesURL) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches = branches;
        this.branchesURL = branchesURL;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public GithubOwner getOwner() {
        return owner;
    }

    void setOwner(GithubOwner owner) {
        this.owner = owner;
    }

    public boolean isFork() {
        return fork;
    }

    void setFork(boolean fork) {
        this.fork = fork;
    }

    public List<GithubBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<GithubBranch> branches) {
        this.branches = branches;
    }

    public String getBranchesURL() {
        return branchesURL;
    }

    public void setBranchesURL(String branchesURL) {
        this.branchesURL = branchesURL;
    }
}
