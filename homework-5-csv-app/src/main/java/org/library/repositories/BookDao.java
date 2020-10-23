package org.library.repositories;

import org.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    void add(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void update(Book book);

    void deleteById(long id);
}
