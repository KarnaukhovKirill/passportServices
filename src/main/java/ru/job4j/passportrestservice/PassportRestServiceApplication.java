package ru.job4j.passportrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class PassportRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportRestServiceApplication.class, args);
    }

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }
}
