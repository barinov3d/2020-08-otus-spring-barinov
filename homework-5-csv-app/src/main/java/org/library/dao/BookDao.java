package org.library.dao;

import org.library.domain.Book;

import java.util.List;

public interface BookDao {

    void insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    void updateTitle(Book book);

    void deleteById(Book book);
}
