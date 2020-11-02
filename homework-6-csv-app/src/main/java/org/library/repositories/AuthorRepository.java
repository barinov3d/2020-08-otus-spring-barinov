package org.library.repositories;

import org.library.models.Author;

import java.util.List;

public interface AuthorRepository {

    Author save(Author author);

    List<Author> findAll();

    Author findByName(String name);

    void updateNameById(long id, String name);

    void deleteById(long id);

    Author findById(long id);

}
