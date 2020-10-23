package org.library.repositories;

import org.library.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    List<Author> findAll();

    Optional<Author> findByName(String name);

}
