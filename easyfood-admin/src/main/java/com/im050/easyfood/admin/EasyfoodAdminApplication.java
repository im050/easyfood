package com.im050.easyfood.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.im050.easyfood")
public class EasyfoodAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyfoodAdminApplication.class, args);
    }

}
