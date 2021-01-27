package org.library.repositories.impl;

import org.library.models.Book;
import org.springframework.data.rest.core.annotation.RestResource;

public interface BookCustomizeRepository<T, ID> {

    boolean isAuthorBookAlreadyExist(Book book);

    @RestResource(path = "name", rel = "name")
    Book findByName(String name);

}
