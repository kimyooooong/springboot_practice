package com.kimyoong.practice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
@Log4j2
@SpringBootApplication
public class PracticeApplication {

    @Value("${server.config}")
    String config;

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("server config : {}", config);
    }

}
