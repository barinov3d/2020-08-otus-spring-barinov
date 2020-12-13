package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookCustomizeRepository<T, ID> {

    List<Book> findAllByAuthor(Author author);

    Optional<Book> findBookByAuthorAndTitle(Author author, String title);

    <S extends T> S save(S entity);

}
