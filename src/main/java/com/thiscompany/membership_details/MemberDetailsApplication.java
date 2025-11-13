package com.thiscompany.membership_details;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MemberDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberDetailsApplication.class, args);
    }
}
