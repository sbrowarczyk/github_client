package com.com.github_api_client.controller;

import com.com.github_api_client.model.projection.GithubRepositoryReadModel;
import com.com.github_api_client.service.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GithubClientController {

    GithubService githubService;

    public GithubClientController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(value = "/repo", produces = "application/json")
    public ResponseEntity<List<GithubRepositoryReadModel>> getRepos(@RequestParam String username) {

        return ResponseEntity.ok(githubService.getRepositories(username));
    }

}
