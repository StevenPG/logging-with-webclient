package com.stevenpg.loggingwithwebclient.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientService {

    @Value("get_endpoint")
    private String getEndpoint;

    @Value("bad_endpoint")
    private String badEndpoint;

    private final WebClient jettyHttpClient;

    public WebClientService(@Qualifier("jettyHttpClient") WebClient jettyHttpClient) {
        this.jettyHttpClient = jettyHttpClient;
    }

    public void testJettyClient() {
        jettyHttpClient.get()
                .uri(getEndpoint)
                .retrieve()
                .bodyToMono(String.class);
    }
}
