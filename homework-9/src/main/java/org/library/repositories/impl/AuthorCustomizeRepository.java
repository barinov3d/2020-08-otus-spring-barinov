package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;

import java.util.Optional;

public interface AuthorCustomizeRepository<T, ID> {

    Optional<Author> findByName(String name);

    <S extends T> S save(S entity);

    Author findAuthorByBook(Book book);

}
