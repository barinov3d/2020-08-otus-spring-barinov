package org.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.library.models.*;
import org.library.repositories.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "dmitry", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "dmitry")
    public void insertData(UserRepository userRepository, AuthorRepository authorRepository, GenreRepository genreRepository,
                           BookRepository bookRepository, CommentRepository commentRepository) {
        //Users
        userRepository.save(new User("test@test.ru", "123456"));
        userRepository.save(new User("ya@ya.ru", "123456"));
        userRepository.save(new User("google@google.com", "123456"));

        Genre genre1 = new Genre("Сomputer science");
        Genre genre2 = new Genre("Romance");
        Genre genre3 = new Genre("Fantasy");
        Genre genre4 = new Genre("Adventure");
        Genre genre5 = new Genre("Drama");
        Genre genre6 = new Genre("Other");
        genreRepository.save(genre1);
        genreRepository.save(genre2);
        genreRepository.save(genre3);
        genreRepository.save(genre4);
        genreRepository.save(genre5);
        genreRepository.save(genre6);

        Comment comment1 = new Comment("It’s a really long book and the author writes like he was the Shakespeare of software developers. " +
                "I find it more useful as a reference to accompany a Udemy class I paid ten bucks for. " +
                "Wish it was a little less wordy so it would be easier to digest.", LocalDate.now());
        Comment comment2 = new Comment("I just took a position as an iOS developer, however since we build off the Oracle Retail frameworks I needed a Java reference to keep nearby. " +
                "This is by far worth every penny. Simple to understand, useful code examples, and fair use of the enormous Java library reachable through a simple import. " +
                "Bruce's examples are clear and he makes intermediate topics easy to grasp. 6 stars.", LocalDate.now());
        Comment comment3 = new Comment("Calling this \"the hard way\" could not be a bigger understatement. I used to think I was a smart person until I got this book." +
                " I can not for the life of me understand why he does certain things, and it is extremely difficult to find answers. I do not recommend this book for beginners because you will want to slam your head into a wall. " +
                "After 4 days and 15+ hours, it is time for me to burn this book and try to find a different resource.", LocalDate.now());
        Comment comment4 = new Comment("As a newbe not only to Python but OOP in general this is by far the best book I have for actually learning the subject through actual programming and not just reading about it. " +
                "It gently eases you in for the first 2 3rds but at the point of learning classes it seems to take off a bit so I've had to slow down at that point.\n" +
                "\n" +
                "If you follow it as it intends it's not a quick book to zoom through but you will learn how to program in Python which is the entire point of the book. " +
                "Take your time with it, there are no short cuts to learning this stuff but this book teaches it well.", LocalDate.now());
        Comment comment5 = new Comment("The book is decent but depends on the videos found online. There are no supplemental PDF's for use offline to troubleshoot the lessons. " +
                "This book is completely dependent on having a fast Internet connection and being online the whole time for video.", LocalDate.now());
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);

        final Author author1 = new Author("Bruce Eckel");
        final Author author2 = new Author("Zed A. Shaw");
        final Author author3 = new Author("Alfred Van Vogt");
        final Author author4 = new Author("Super Author");

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);

        final Book book1 = new Book("Thinking in java", genre1, author1);
        book1.getComments().addAll(List.of(comment1, comment2));
        final Book book2 = new Book("Learn Python the Hard Way", genre1, author2);
        book2.getComments().addAll(List.of(comment3, comment4, comment5));
        final Book book3 = new Book("The Monster", genre2, author3);
        book3.getComments().addAll(Collections.emptyList());

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        author1.setBooks(List.of(book1));
        author2.setBooks(List.of(book2));
        author3.setBooks(List.of(book3));

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);

    }
}
