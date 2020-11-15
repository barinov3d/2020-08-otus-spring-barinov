package org.library.repositories;

import org.library.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void add(Author author);

    List<Author> findAll();

    Optional<Author> findByName(String name);

}
