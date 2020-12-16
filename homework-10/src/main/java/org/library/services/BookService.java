package org.library.services;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.BookNotFoundException;
import org.library.exceptions.DuplicateAuthorBookException;
import org.library.models.Author;
import org.library.models.Book;

import java.util.List;

public interface BookService {

    long count();

    Book findByTitle(String name) throws BookNotFoundException;

    Book findById(String id) throws BookNotFoundException;

    void deleteById(String id);

    void deleteAll(List<Book> books);

    List<Book> findAll();

    List<Book> findAllByAuthor(Author author) throws AuthorNotFoundException;

    Book findBookByAuthorAndTitle(Author author, String title) throws BookNotFoundException;

    Book save(Book book) throws DuplicateAuthorBookException;

}
