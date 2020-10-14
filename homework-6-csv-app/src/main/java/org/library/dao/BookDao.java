package org.library.dao;

import org.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    long count();

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void updateTitleById(long id, String title);

    void updateCommentById(long id, String comment);

    void deleteById(long id);
}
