package com.thiscompany.membership_details;

import com.thiscompany.membership_details.config.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(KeycloakProperties.class)
public class MemberDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberDetailsApplication.class, args);
    }
}
