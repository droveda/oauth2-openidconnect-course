package com.droveda.bugtrackercli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BugtrackercliApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugtrackercliApplication.class, args);
    }

}
