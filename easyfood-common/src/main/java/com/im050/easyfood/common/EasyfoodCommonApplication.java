package com.im050.easyfood.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.im050.easyfood")
public class EasyfoodCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyfoodCommonApplication.class, args);
    }

}
