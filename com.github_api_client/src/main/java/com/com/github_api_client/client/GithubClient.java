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

    public List<GithubRepository> getRepositories(String githubUsername) {

        String githubURL = String.format("https://api.github.com/users/%s/repos", githubUsername);
        GithubRepository[] repositories = restTemplate.getForEntity(githubURL, GithubRepository[].class).getBody();
        return Arrays.stream(repositories).toList();
    }

    public void loadBranches(GithubRepository githubRepository) {

        GithubBranch[] branches = restTemplate.getForEntity(githubRepository.getBranchesURL(), GithubBranch[].class).getBody();
        githubRepository.setBranches(Arrays.stream(branches).toList());
    }

    public void isUserExist(String username) {

        try {
            restTemplate.getForEntity(String.format("https://api.github.com/users/%s", username), Object.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The user with the given username does not exist");
        }
    }


}
