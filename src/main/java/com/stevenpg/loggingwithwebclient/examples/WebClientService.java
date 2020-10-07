package com.stevenpg.loggingwithwebclient.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientService {

    Logger logger = LoggerFactory.getLogger(WebClientService.class);

    private final WebClient jettyHttpClient;
    private final ConfigProperties configProperties;

    @Autowired
    public WebClientService(ConfigProperties configProperties, @Qualifier("jettyHttpClient") WebClient jettyHttpClient) {
        this.configProperties = configProperties;
        this.jettyHttpClient = jettyHttpClient;
    }

    public void testJettyClient() {
        var result = jettyHttpClient.get()
                .uri(configProperties.getGetEndpoint())
                .retrieve()
                .toBodilessEntity()
                .block();
        logger.info("URI: " + configProperties.getGetEndpoint());
        logger.info("Response: " + result.getStatusCode().toString());

    }
}
