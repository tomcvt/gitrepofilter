package com.tomcvt.gitrepofilter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @Test
    void Test1() {
        stubFor(get(urlEqualTo("/some/endpoint"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"key\": \"value\" }")));
    }
}
