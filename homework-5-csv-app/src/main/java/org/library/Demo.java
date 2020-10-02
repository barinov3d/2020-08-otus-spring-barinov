package org.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
//@EnableConfigurationProperties()
public class Demo {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Demo.class);
    }

}
