package com.com.github_api_client.model.projection;

import com.com.github_api_client.model.GithubRepository;

import java.util.List;


public class GithubRepositoryReadModel {

    private String repositoryName;
    private String owner;
    private List<GithubBranchReadModel> branches;

    public GithubRepositoryReadModel(GithubRepository githubRepository) {
        this.repositoryName = githubRepository.getName();
        this.owner = githubRepository.getOwner().getLogin();
        this.branches = githubRepository.getBranches().stream().map(branch -> new GithubBranchReadModel(branch)).toList();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<GithubBranchReadModel> getBranches() {
        return branches;
    }

    public void setBranches(List<GithubBranchReadModel> branches) {
        this.branches = branches;
    }
}
