package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.rest.core.annotation.RestResource;


public interface AuthorCustomizeRepository<T, ID> {

    @RestResource(path = "name", rel = "name")
    Author findByName(String name);

    Author findAuthorByBook(Book book);

}
