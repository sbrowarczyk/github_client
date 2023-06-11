package com.com.github_api_client.client;

import com.com.github_api_client.model.GithubBranch;
import com.com.github_api_client.model.GithubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubClient {

    private final WebClient webClient;

    public GithubClient(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder().baseUrl("https://api.github.com").build();
    }

    /**
     * Get all user repositories
     *
     * @param githubUsername user github username
     * @return repository list
     */
    public List<GithubRepository> getRepositories(String githubUsername) {

        return Arrays.stream(webClient.get().uri("/users/{username}/repos", githubUsername).retrieve().bodyToMono(GithubRepository[].class).block()).toList();
    }

    /**
     * Get all branches for given repository
     *
     * @param githubRepository github repository to load all it branches
     */
    public List<GithubBranch> loadBranches(GithubRepository githubRepository) {

        return Arrays.stream(webClient.get().uri(githubRepository.getBranchesURL()).retrieve().bodyToMono(GithubBranch[].class).block()).toList();
    }

    /**
     * Check if the user exists. If a user does not exist then throw an exception
     *
     * @param username GitHub username to check if user exist
     * @throws ResponseStatusException if user does not exist then it throws exception, that is handled by ExceptionHandler
     */
    public void isUserExist(String username) throws ResponseStatusException {
        webClient.get().uri("/users/{username}", username).retrieve().onStatus(
                        httpStatusCode -> httpStatusCode.equals(HttpStatus.NOT_FOUND),
                        clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with the given username does not exist")))
                .bodyToMono(Object.class).block();
    }
}
