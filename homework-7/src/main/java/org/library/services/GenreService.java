package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.GenreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    /**
     * Creates genre
     */
    public void createGenre(String genreName) {
        final Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalGenre.isPresent()) {
            throw new RuntimeException("Genre with name: " + genreName + "' already exist");
        }
        Genre genre = new Genre(0, genreName);
        genreRepository.save(genre);
    }

    /**
     * Finds by id
     */
    public Genre findById(long id) {
        final Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            genreNotFound(id);
        }
        return optionalGenre.get();
    }

    /**
     * Finds all genres
     */
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    /**
     * Updates genre name
     */
    public void updateName(long id, String name) {
        final Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            genreNotFound(id);
        }
        final Genre genre = genreRepository.findById(id).get();
        genre.setName(name);
        genreRepository.save(genre);
    }

    /**
     * Deletes genre by id
     */
    public void deleteGenre(long id) {
        final Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            genreNotFound(id);
            return;
        }
        final Genre genre = genreRepository.findById(id).get();
        genreRepository.delete(genre);
    }

    private void genreNotFound(long id) {
        throw new GenreNotFoundException("Genre with id '" + id + "' not exist");
    }

}

