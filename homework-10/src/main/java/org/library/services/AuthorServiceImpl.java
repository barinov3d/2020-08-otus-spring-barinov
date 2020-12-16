package org.library.services;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.DuplicateAuthorNameException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    final BookRepository bookRepository;
    final AuthorRepository authorRepository;

    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) {
        final Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id: %s not found", id)));
        final List<Book> books = author.getBooks();
        bookRepository.deleteAll(books);
        authorRepository.deleteById(id);
    }

    @Override
    public void delete(Author author) {
        final List<Book> books = author.getBooks();
        bookRepository.deleteAll(books);
        authorRepository.delete(author);
    }

    @Override
    public Author findByName(String name) {
        final Author author = authorRepository.findByName(name);
        if (author == null) {
            throw new AuthorNotFoundException(String.format("Author with name: %s not found", name));
        }
        return author;
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null && (authorRepository.findByName(author.getName()) != null)) {
            throw new DuplicateAuthorNameException(
                    "Author with name '" + author.getName() + "' is already define in the scope");
        }
        return authorRepository.save(author);
    }
}
