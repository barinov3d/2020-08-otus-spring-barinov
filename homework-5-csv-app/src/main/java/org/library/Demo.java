package org.library;

import org.h2.tools.Console;
import org.library.dao.AuthorDao;
import org.library.dao.BookDao;
import org.library.dao.GenreDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
//@EnableConfigurationProperties()
public class Demo {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Demo.class);
        BookDao bookDao = context.getBean(BookDao.class);
        AuthorDao authorDao = context.getBean(AuthorDao.class);
        GenreDao genreDao = context.getBean(GenreDao.class);
        System.out.println("All books " + bookDao.getAll());
        System.out.println("Book " + bookDao.getById(1));
        //System.out.println("All authors " + authorDao.getAll());
        //System.out.println("All genre " + genreDao.getAll());
        Console.main(args);
    }

}
