package org.library.services;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.DuplicateAuthorNameException;
import org.library.models.Author;
import org.library.models.Book;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(String id) throws AuthorNotFoundException;

    void deleteById(String id) throws AuthorNotFoundException;

    void delete(Author author) throws AuthorNotFoundException;

    Author findByName(String name) throws AuthorNotFoundException;

    Author save(Author author) throws DuplicateAuthorNameException;

    Author findAuthorByBook(Book book) throws AuthorNotFoundException;
}
