package com.tomcvt.gitrepofilter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "gitrepo.filter.github.api.base-url=http://localhost:8090"
    })
@AutoConfigureTestRestTemplate

public class IntegrationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8090); // choose a fixed port
        wireMockServer.start();
        configureFor("localhost", 8090);
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void Test1() {
        stubFor(get(urlEqualTo("/users/testuser/repos"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [
                    {
                        "name": "backend",
                        "fork": false,
                        "owner": { "login": "john" }
                    },
                    {
                        "name": "frontend",
                        "fork": false,
                        "owner": { "login": "testuser" }
                    },
                    {
                        "name": "forked-repo",
                        "fork": true,
                        "owner": { "login": "john" }
                    }
                    ]
                """.replace(" ", "").replace("\n", ""))));
        stubFor(get(urlEqualTo("/repos/john/backend/branches"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [
                    { "name": "main", "commit": { "sha": "abc123" } },
                    { "name": "dev", "commit": { "sha": "def456" } }
                    ]
                """.replace(" ", "").replace("\n", ""))));
        stubFor(get(urlEqualTo("/repos/testuser/frontend/branches"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [
                    { "name": "master", "commit": { "sha": "ghi789" } }
                    ]
                """.replace(" ", "").replace("\n", ""))));

        String url = "http://localhost:" + port + "/api/filter-repositories/testuser";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    
        assertThat(response.getBody()).isEqualTo("""
            [
                {
                    "name": "backend",
                    "ownerLogin": "john",
                    "branches": [
                        { "name": "main", "commitSha": "abc123" },
                        { "name": "dev", "commitSha": "def456" }
                    ]
                },
                {
                    "name": "frontend",
                    "ownerLogin": "testuser",
                    "branches": [
                        { "name": "master", "commitSha": "ghi789" }
                    ]
                }
            ]
        """.replace(" ", "").replace("\n", ""));
    
    }
}