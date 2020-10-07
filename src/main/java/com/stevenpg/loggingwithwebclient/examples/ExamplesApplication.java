package com.stevenpg.loggingwithwebclient.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamplesApplication {

    final WebClientService webClientService;

    public ExamplesApplication(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamplesApplication.class, args);
    }

}
