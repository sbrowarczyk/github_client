package com.com.github_api_client.controller;

import com.com.github_api_client.exceptionhandler.ExceptionInfo;
import com.com.github_api_client.model.GithubBranch;
import com.com.github_api_client.model.GithubCommit;
import com.com.github_api_client.model.GithubOwner;
import com.com.github_api_client.model.GithubRepository;
import com.com.github_api_client.model.projection.GithubRepositoryReadModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private MockRestServiceServer mockServer;

    private final String username = "user";
    private URI uri;


    @BeforeEach
    public void init() {
        String url = "http://localhost:" + port;
        uri = UriComponentsBuilder.fromHttpUrl(url).path("/repo")
                .queryParam("username", username).build().toUri();


        mockServer = MockRestServiceServer.createServer(this.restTemplate);
    }

    @Test
    void httpGet_userNotExist_return404andMessage() {
        //given
        mockServer.expect(ExpectedCount.once(),
                        requestTo(String.format("https://api.github.com/users/%s", username)))
                .andRespond(withStatus(NOT_FOUND));
        //when
        ResponseEntity<ExceptionInfo> exceptionInfo = testRestTemplate.getForEntity(uri, ExceptionInfo.class);
        //then
        assertThat(exceptionInfo.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(exceptionInfo.getBody().getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(exceptionInfo.getBody().getMessage()).contains("username does not exist");
    }

    @Test
    public void httpGet_headerIsApplicationXML_return406andMessage() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        //when
        ResponseEntity<ExceptionInfo> exceptionInfo = testRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ExceptionInfo.class);
        //then
        assertThat(exceptionInfo.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(exceptionInfo.getBody().getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
        assertThat(exceptionInfo.getBody().getMessage()).contains("The server cannot produce");
    }

    @Test
    public void httpGet_returnAllRepos() throws JsonProcessingException {
        //given
        ObjectMapper mapper = new ObjectMapper();
        //and
        List<GithubRepository> repositories = getRepositories();
        //and
        mockServer.expect(ExpectedCount.once(),
                        requestTo(String.format("https://api.github.com/users/%s", username)))
                .andRespond(withStatus(OK));
        //and
        mockServer.expect(ExpectedCount.once(),
                        requestTo(String.format("https://api.github.com/users/%s/repos", username))).
                andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(repositories)));
        //and
        mockServer.expect(ExpectedCount.once(), requestTo(repositories.get(0).getBranchesURL())).andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(getBranches())));
        //and
        mockServer.expect(ExpectedCount.once(), requestTo(repositories.get(1).getBranchesURL())).andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(getBranches())));
        //and
        repositories.forEach(githubRepository -> githubRepository.setBranches(getBranches()));
        List<GithubRepositoryReadModel> readModelRepositories = repositories.stream().map(GithubRepositoryReadModel::new).toList();
        //when
        ResponseEntity<GithubRepositoryReadModel[]>
                responseEntityRepositories = testRestTemplate.getForEntity(uri, GithubRepositoryReadModel[].class);
        List<GithubRepositoryReadModel> responseReadModelRepositories = Arrays.stream(responseEntityRepositories.getBody()).toList();
        //then
        assertThat(responseEntityRepositories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseReadModelRepositories)
                .usingRecursiveComparison().
                isEqualTo(responseReadModelRepositories);
    }

    public List<GithubRepository> getRepositories() {
        return List.of(createRepo("repo1"), createRepo("repo2"));
    }

    private GithubRepository createRepo(String repoName) {
        GithubOwner githubOwner = new GithubOwner(username);
        String branchesURL = String.format("https://api.github.com/repos/%s/%s/branches", username, repoName);
        return new GithubRepository("repo1", githubOwner, false, null, branchesURL);
    }

    private List<GithubBranch> getBranches() {
        GithubCommit githubCommit = new GithubCommit("123");
        GithubBranch branch1 = new GithubBranch("branch1", githubCommit);
        GithubBranch branch2 = new GithubBranch("branch2", githubCommit);
        return List.of(branch1, branch2);
    }


}
