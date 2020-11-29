package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.repositories.AuthorRepository;
import org.library.services.exceptions.AuthorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Creates author
     */
    public void createAuthor(String authorName) {
        authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("Author with name: " + authorName + "' already exist"));
        Author author = new Author(authorName, null);
        authorRepository.save(author);
    }

    /**
     * Finds by id
     */
    public Author findById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id '" + id + "' not exist"));
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
    public void updateName(String id, String name) {
        final Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id '" + id + "' not exist"));
        author.setName(name);
        authorRepository.save(author);
    }

    /**
     * Deletes author by id
     */
    public void deleteAuthor(String id) {
        final Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id '" + id + "' not exist"));
        authorRepository.delete(author);
    }

}

