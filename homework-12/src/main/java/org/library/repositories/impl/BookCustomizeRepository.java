package org.library.repositories.impl;

import org.library.models.Book;

public interface BookCustomizeRepository<T, ID> {

    boolean isAuthorBookAlreadyExist(Book book);

    Book findByName(String name);

}
