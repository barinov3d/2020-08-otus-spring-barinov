package org.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "org.library.repositories")
@EnableMongock
@SpringBootApplication
public class Demo {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Demo.class);
        //MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
        //mongoTemplate.indexOps("authors").ensureIndex(new Index("name", Sort.Direction.ASC).unique());
        //mongoTemplate.indexOps("genres").ensureIndex(new Index("name", Sort.Direction.ASC).unique());

        System.out.println("\n\n\n----------------------------------------------\n\n");
        System.out.println("Книги в БД:");
        BookRepository bookRepository = context.getBean(BookRepository.class);
        bookRepository.findAll().forEach(System.out::println);
        System.out.println("\n\n----------------------------------------------");

        System.out.println("Авторы в БД:");
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        authorRepository.findAll().forEach(System.out::println);
        System.out.println("\n\n----------------------------------------------");

        System.out.println("Жанры в БД:");
        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        genreRepository.findAll().forEach(System.out::println);
        System.out.println("\n\n----------------------------------------------");

        System.out.println("Комментарии в БД:");
        CommentRepository commentRepository = context.getBean(CommentRepository.class);
        commentRepository.findAll().forEach(System.out::println);
        System.out.println("\n\n----------------------------------------------");
    }

}
