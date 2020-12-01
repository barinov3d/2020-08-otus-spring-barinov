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
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "dmitry", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "stvort")
    public void insertData(AuthorRepository authorRepository, GenreRepository genreRepository,
                           BookRepository bookRepository, CommentRepository commentRepository) {
        final Author author1 = new Author("Bruce Eckel");
        final Author author2 = new Author("Zed A. Shaw");
        final Author author3 = new Author("Alfred Van Vogt");
        final Author author4 = new Author("Super Author");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);

        Genre genre1 = new Genre("Сomputer science");
        Genre genre2 = new Genre("Other");
        genreRepository.save(genre1);
        genreRepository.save(genre2);

        Comment comment1 = new Comment("It's an interesting book", LocalDate.now());
        Comment comment2 = new Comment("It's a great book!", LocalDate.now());
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        Book book1 = new Book("Thinking in java", author1, genre1);
        comment1.setBook(book1);
        final List<Book> author1Books = author1.getBooks();
        bookRepository.save(book1);
        author1Books.add(book1);
        author1.setBooks(author1Books);
        authorRepository.save(author1);
        book1.setComments(List.of(comment1));

        Book book2 = new Book("Learn Python the Hard Way", author2, genre1);
        comment2.setBook(book2);
        final List<Book> author2Books = author2.getBooks();
        bookRepository.save(book2);
        author2Books.add(book2);
        author2.setBooks(author2Books);
        authorRepository.save(author2);
        book2.setComments(List.of(comment2));

        Book book3 = new Book("The Monster", author3, genre2);
        final List<Book> author3Books = author3.getBooks();
        bookRepository.save(book3);
        author3Books.add(book3);
        author3.setBooks(author3Books);
        authorRepository.save(author3);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }
}
