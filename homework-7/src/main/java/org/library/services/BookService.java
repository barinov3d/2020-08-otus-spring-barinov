package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.AuthorNotFoundException;
import org.library.services.exceptions.BookNotFoundException;
import org.library.services.exceptions.GenreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    /**
     * Creates book
     */
    public void createBook(String title, String authorName, String genreName
    ) {
        final Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        final Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalAuthor.isEmpty()) {
            authorNotFound(authorName);
        }
        if (optionalGenre.isEmpty()) {
            throw new GenreNotFoundException("Genre with name '" + genreName + "' not exist");
        }
        Book book = new Book(0, title, null, optionalAuthor.get(), optionalGenre.get());
        bookRepository.save(book);
    }

    /**
     * Finds by id
     */
    public Book findById(long id) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            bookNotFound(id);
        }
        return optionalBook.get();
    }

    /**
     * Finds all books
     */
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Updates book title
     */
    public void updateTitle(long id, String title) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            bookNotFound(id);
        }
        final Book book = bookRepository.findById(id).get();
        book.setTitle(title);
        bookRepository.save(book);
    }

    /**
     * Deletes book by id
     */
    public void deleteBook(long id) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            bookNotFound(id);
            return;
        }
        final Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
    }

    /**
     * Find all author books
     */
    public List<Book> findAllAuthorBooks(String authorName) {
        final Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isEmpty()) {
            authorNotFound(authorName);
        }
        return bookRepository.findAllByAuthor(optionalAuthor.get());
    }

    private void bookNotFound(long id) {
        throw new BookNotFoundException("Book with id '" + id + "' not exist");
    }

    private void authorNotFound(String authorName) {
        throw new AuthorNotFoundException("Author with name '" + authorName + "' not exist");
    }
}

