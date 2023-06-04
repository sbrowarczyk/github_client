package com.com.github_api_client.client;

import com.com.github_api_client.model.GithubBranch;
import com.com.github_api_client.model.GithubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubClient {

    private final RestTemplate restTemplate;

    public GithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get all user repositories
     *
     * @param githubUsername user github username
     * @return repository list
     */
    public List<GithubRepository> getRepositories(String githubUsername) {

        String githubURL = String.format("https://api.github.com/users/%s/repos", githubUsername);
        GithubRepository[] repositories = restTemplate.getForEntity(githubURL, GithubRepository[].class).getBody();
        return Arrays.stream(repositories).toList();
    }

    /**
     * Load all branches for given repository
     * @param githubRepository github repository to load all it branches
     */
    public void loadBranches(GithubRepository githubRepository) {

        GithubBranch[] branches = restTemplate.getForEntity(githubRepository.getBranchesURL(), GithubBranch[].class).getBody();
        githubRepository.setBranches(Arrays.stream(branches).toList());
    }

    /**
     * Check if the user exists. If a user does not exist then throw an exception, otherwise do nothing
     *
     * @param username GitHub username to check if user exist
     * @throws ResponseStatusException if user does not exist then it throws exception, that is handled by ExceptionHandler
     */
    public void isUserExist(String username) throws ResponseStatusException {

        try {
            restTemplate.getForEntity(String.format("https://api.github.com/users/%s", username), Object.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with the given username does not exist");
        }
    }


}
