package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;

public interface AuthorCustomizeRepository<T, ID> {

    Author findByName(String name);

    Author findAuthorByBook(Book book);

}
