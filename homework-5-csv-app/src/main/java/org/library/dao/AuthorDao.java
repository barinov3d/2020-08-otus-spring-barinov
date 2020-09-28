package org.library.dao;

import org.library.domain.Author;
import org.library.domain.Book;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    Book getById(long id);

    List<Author> getAll();

    void update(Author author);

    void deleteById(Author author);
}
