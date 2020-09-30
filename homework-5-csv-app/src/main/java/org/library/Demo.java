package org.library;

import org.library.dao.BookDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@EnableConfigurationProperties()
public class Demo {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Demo.class);
        BookDao bookDao = context.getBean(BookDao.class);
        System.out.println("All books " + bookDao.getAll());

    }

}
