package org.library.dao;

import org.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void add(Author author);

    List<Author> findAll();

    Optional<Author> findByName(String name);

}
