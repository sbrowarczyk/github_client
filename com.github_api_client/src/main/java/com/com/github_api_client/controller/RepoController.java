package com.com.github_api_client.controller;

import com.com.github_api_client.model.projection.GithubRepositoryReadModel;
import com.com.github_api_client.service.GithubService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RepoController {

    GithubService githubService;

    public RepoController(GithubService githubService) {
        this.githubService = githubService;
    }


    @GetMapping(value = "/repo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GithubRepositoryReadModel>> getRepos(@RequestParam String username) {
        return ResponseEntity.ok(githubService.getRepositories(username));
    }

}
