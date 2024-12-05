package com.example.exemploteste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.exemploteste.model")
@EnableJpaRepositories("com.example.exemploteste.repository")
public class ExemplotesteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExemplotesteApplication.class, args);
    }
}
