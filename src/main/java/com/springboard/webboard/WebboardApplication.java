package com.springboard.webboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebboardApplication.class, args);
    }

}