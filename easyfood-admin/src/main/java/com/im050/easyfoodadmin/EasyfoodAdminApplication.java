package com.im050.easyfoodadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.im050")
public class EasyfoodAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyfoodAdminApplication.class, args);
    }

}