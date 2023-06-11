package com.com.github_api_client.service;

import com.com.github_api_client.client.GithubClient;
import com.com.github_api_client.model.GithubRepository;
import com.com.github_api_client.model.projection.GithubRepositoryReadModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {

    GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }
    /**
     * @param username github user
     * @return complete user repository list
     */
    public List<GithubRepositoryReadModel> getRepositories(String username) {

        githubClient.isUserExist(username);
        List<GithubRepository> repositories = githubClient.getRepositories(username)
                .stream().filter(githubRepository -> !githubRepository.isFork()).toList();
        loadBranches(repositories);
        return repositories.stream().map(repository -> new GithubRepositoryReadModel(repository)).toList();
    }

    /**
     * Load branches for every repository in user repository list
     *
     * @param repositories repository list
     */
    public void loadBranches(List<GithubRepository> repositories) {

        repositories.forEach(githubRepository -> {
            githubRepository.setBranchesURL(githubRepository.getBranchesURL()
                    .replace("{/branch}", ""));
            githubRepository.setBranches(githubClient.loadBranches(githubRepository));
                }
        );
    }


}
