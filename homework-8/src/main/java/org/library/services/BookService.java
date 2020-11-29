package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Book;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.AuthorNotFoundException;
import org.library.services.exceptions.BookNotFoundException;
import org.library.services.exceptions.GenreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Book book = new Book(title, null, authorRepository.findByName(authorName)
                .orElseThrow(() -> new AuthorNotFoundException("Author with name '" + authorName + "' not exist")),
                genreRepository.findByName(genreName)
                        .orElseThrow(() -> new GenreNotFoundException("Genre with name '" + genreName + "' not exist")));
        bookRepository.save(book);
    }

    /**
     * Finds by id
     */
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book with id '" + id + "' not exist"));
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
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' not exist"));
        book.setTitle(title);
        bookRepository.save(book);
    }

    /**
     * Deletes book by id
     */
    public void deleteBook(long id) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' not exist"));
        bookRepository.delete(book);
    }

}

