package org.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties()
public class Demo {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Demo.class, args);
    }

}
