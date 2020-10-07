package com.stevenpg.loggingwithwebclient.examples;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Slf4j
@Component
public class WebClients {

    Logger logger = LoggerFactory.getLogger(WebClients.class);

    // Add exchange filters

    // logging with jetty httpclient
    // Needs jetty-reactive-httpclient
    @Bean
    public WebClient jettyHttpClient() {
        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        HttpClient httpClient = new HttpClient(sslContextFactory) {
            @Override
            public Request newRequest(URI uri) {
                Request request = super.newRequest(uri);
                return enhance(request);
            }
        };

        return WebClient.builder().clientConnector(new JettyClientHttpConnector(httpClient)).build();
    }

    /**
     * Method that adds logging to the incoming request at each interval
     */
    private Request enhance(Request inboundRequest) {
        StringBuilder log = new StringBuilder();
        // Request Logging
        inboundRequest.onRequestBegin(request ->
                log.append("URI: ")
                .append(request.getURI())
                .append("\n")
                .append("Method: ")
                .append(request.getMethod()));
        inboundRequest.onRequestHeaders(request -> {
            log.append("Headers:\n");
            for (HttpField header : request.getHeaders()) {
                log.append("Header -> " + header.getName() + " : " + header.getValue());
            }
        });
        inboundRequest.onRequestContent((request, content) ->
                log.append("Body: \n\t")
                .append(content.toString()));
        log.append("\n");

        // Response Logging
        inboundRequest.onResponseBegin(response ->
                log.append("Status: ")
                .append(response.getStatus()));
        inboundRequest.onResponseHeaders(response -> {
           log.append("Headers:\n");
           for (HttpField header : response.getHeaders()) {
               log.append("Header -> " + header.getName() + " : " + header.getValue());
           }
        });
        inboundRequest.onResponseContent(((response, content) -> {
            log.append("Response Body: " + content.toString());
        }));

        // Add actual log invocation
        inboundRequest.onRequestSuccess(request -> logger.debug(log.toString()));
        inboundRequest.onResponseSuccess(response -> logger.debug(log.toString()));

        // Return original request
        return inboundRequest;
    }

    // logging with netty httpclient
}
