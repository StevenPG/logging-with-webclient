package com.stevenpg.loggingwithwebclient.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebClientServiceRunner implements CommandLineRunner {

    @Autowired
    WebClientService webClientService;

    @Override
    public void run(String... args) throws Exception {

    }
}
