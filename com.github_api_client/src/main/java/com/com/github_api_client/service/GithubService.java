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
     * Check if the user exist, get all user repository, fill every repository with branches,
     *
     * @param username github user
     * @return complete user repository list
     */
    public List<GithubRepositoryReadModel> getRepositories(String username) {

        checkIfUserExist(username);
        List<GithubRepository> repositories = githubClient.getRepositories(username)
                .stream().filter(githubRepository -> !githubRepository.isFork()).toList();
        loadBranches(repositories);
        return repositories.stream().map(repository -> new GithubRepositoryReadModel(repository)).toList();
    }

    /**
     * Load branches for every repository in user repository list
     *
     * @param repositories  repository list
     */
    public void loadBranches(List<GithubRepository> repositories) {

        repositories.forEach(githubRepository -> {
                    githubRepository.setBranchesURL(githubRepository.getBranchesURL()
                            .replace("{/branch}", ""));
                    //extract link to repository branches from filed "branches_url", by deliting "{/branch}" at link's end
                    githubClient.loadBranches(githubRepository);
                }
        );
    }
    /**
     * Check if the user exists. If a user does not exist then throw an exception, otherwise do nothing
     *
     * @param username GitHub username to check if user exist
     */
    public void checkIfUserExist(String username){
        githubClient.isUserExist(username);
    }

}
