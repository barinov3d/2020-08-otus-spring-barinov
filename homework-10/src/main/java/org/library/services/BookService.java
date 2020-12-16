package org.library.services;

import org.library.models.Author;
import org.library.models.Book;

import java.util.List;

public interface BookService {

    long count();

    Book findById(String id);

    void deleteById(String id);

    void deleteAll(List<Book> books);

    List<Book> findAll();

    List<Book> findAllByAuthor(Author author);

    Book findBookByAuthorAndTitle(Author author, String title);

    Book save(Book book);

}
