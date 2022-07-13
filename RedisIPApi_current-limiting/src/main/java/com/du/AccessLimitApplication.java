package com.du;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AccessLimitApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AccessLimitApplication.class, args);
        for (String s : run.getBeanDefinitionNames()) {
            System.out.println(s);
        }
    }

}
