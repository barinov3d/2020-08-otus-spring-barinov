package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.models.Book;
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
    public Book createBook(String title, String authorName, String genreName
    ) {
        final Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        Book book = new Book(title, optionalAuthor
                .orElseThrow(() -> new AuthorNotFoundException("Author with name '" + authorName + "' not exist")),
                genreRepository.findByName(genreName)
                        .orElseThrow(() -> new GenreNotFoundException("Genre with name '" + genreName + "' not exist")));
        final Author author = optionalAuthor.get();
        final List<Book> authorBooks = author.getBooks();
        bookRepository.save(book);
        authorBooks.add(book);
        author.setBooks(authorBooks);
        authorRepository.save(author);
        return book;
    }

    /**
     * Finds by id
     */
    public Book findById(String id) {
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
    public void updateTitle(String id, String title) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' not exist"));
        book.setTitle(title);
        bookRepository.save(book);
    }

    /**
     * Deletes book by id
     */
    public void deleteBook(String id) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' not exist"));
        bookRepository.delete(book);
    }

}

