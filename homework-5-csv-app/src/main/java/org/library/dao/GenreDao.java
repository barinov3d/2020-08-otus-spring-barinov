package org.library.dao;

import org.library.domain.Book;
import org.library.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    Book getById(long id);

    List<Genre> getAll();

    void update(Genre genre);

    void deleteById(Genre genre);
}
