package com.example.prgeksamenbackendtest;

import com.example.prgeksamenbackendtest.inititalization.UserInit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PrgEksamenBackendTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrgEksamenBackendTestApplication.class, args);
    }
    @Bean
    CommandLineRunner init(UserInit userInit) {
        return args -> {
            userInit.initializeAdminAccount();
            userInit.initializeUserAccount();
        };
    }

}
