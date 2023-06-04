package com.com.github_api_client.service;

import com.com.github_api_client.client.GithubClient;
import com.com.github_api_client.model.GithubRepository;
import com.com.github_api_client.model.projection.GithubRepositoryReadModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {

    GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<GithubRepositoryReadModel> getRepositories(String username) {

        checkIfUserExist(username);
        List<GithubRepository> repositories = githubClient.getRepositories(username)
                .stream().filter(githubRepository -> !githubRepository.isFork()).toList();
        getBranches(repositories);
        return repositories.stream().map(repository -> new GithubRepositoryReadModel(repository)).toList();
    }

    public void getBranches(List<GithubRepository> repositories) {

        repositories.forEach(githubRepository -> {
                    githubRepository.setBranchesURL(githubRepository.getBranchesURL()
                            .replace("{/branch}", ""));
                    githubClient.loadBranches(githubRepository);
                }
        );
    }

    public void checkIfUserExist(String username){
        githubClient.isUserExist(username);
    }

}
