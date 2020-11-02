package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;

import java.util.List;

public interface BookRepository {

    long count();

    Book save(Book book);

    Book findById(long id);

    List<Book> findAll();

    List<Book> findAll(Author author);

    void updateTitleById(long id, String title);

    void updateCommentById(long id, String comment);

    void deleteById(long id);

}
