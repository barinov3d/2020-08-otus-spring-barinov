package org.library.dao;

import org.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    void insert(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void updateTitle(Book book);

    void deleteById(long id);
}
