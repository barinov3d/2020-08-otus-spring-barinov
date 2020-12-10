package org.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;

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
    public void insertData(AuthorRepository authorRepository, GenreRepository genreRepository,
                           BookRepository bookRepository, CommentRepository commentRepository) {

        Genre genre1 = new Genre("Сomputer science");
        Genre genre2 = new Genre("Other");
        genreRepository.save(genre1);
        genreRepository.save(genre2);

        Comment comment1 = new Comment("It's an interesting book", LocalDate.now());
        Comment comment2 = new Comment("It's a great book!", LocalDate.now());
        Comment comment3 = new Comment("Boring book...", LocalDate.now());
        Comment comment4 = new Comment("Pretty nice", LocalDate.now());
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

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
        book2.getComments().addAll(List.of(comment1, comment3, comment4));
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
