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

    String getRepositoryName() {
        return repositoryName;
    }

    void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    String getOwner() {
        return owner;
    }

    void setOwner(String owner) {
        this.owner = owner;
    }

    List<GithubBranchReadModel> getBranches() {
        return branches;
    }

    void setBranches(List<GithubBranchReadModel> branches) {
        this.branches = branches;
    }
}
