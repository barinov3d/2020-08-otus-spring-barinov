package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.AuthorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    /**
     * Creates author
     */
    public void createAuthor(String authorName) {
        final Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isPresent()) {
            throw new RuntimeException("Author with name: " + authorName + "' already exist");
        }
        Author author = new Author(0, authorName, null);
        authorRepository.save(author);
    }

    /**
     * Finds by id
     */
    public Author findById(long id) {
        final Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            authorNotFound(id);
        }
        return optionalAuthor.get();
    }

    /**
     * Finds all authors
     */
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    /**
     * Updates author name
     */
    public void updateName(long id, String name) {
        final Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            authorNotFound(id);
        }
        final Author author = authorRepository.findById(id).get();
        author.setName(name);
        authorRepository.save(author);
    }

    /**
     * Deletes author by id
     */
    public void deleteAuthor(long id) {
        final Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            authorNotFound(id);
            return;
        }
        final Author author = authorRepository.findById(id).get();
        authorRepository.delete(author);
    }

    private void authorNotFound(long id) {
        throw new AuthorNotFoundException("Author with id '" + id + "' not exist");
    }

}

