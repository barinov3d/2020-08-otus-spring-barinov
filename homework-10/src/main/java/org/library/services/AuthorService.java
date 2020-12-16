package org.library.services;

import org.library.models.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(String id);

    void deleteById(String id);

    void delete(Author author);

    Author findByName(String name);

    Author save(Author author);
}
