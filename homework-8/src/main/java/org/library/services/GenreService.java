package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Genre;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.GenreNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    /**
     * Creates genre
     */
    public void createGenre(String genreName) {
        genreRepository.findByName(genreName)
                .orElseThrow(() -> new RuntimeException("Genre with name: " + genreName + "' already exist"));
        Genre genre = new Genre( genreName);
        genreRepository.save(genre);
    }

    /**
     * Finds by id
     */
    public Genre findById(String id) {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre with id '" + id + "' not exist"));
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
    public void updateName(String id, String name) {
        final Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id '" + id + "' not exist"));
        genre.setName(name);
        genreRepository.save(genre);
    }

    /**
     * Deletes genre by id
     */
    public void deleteGenre(String id) {
        final Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id '" + id + "' not exist"));
        genreRepository.delete(genre);
    }

    private void genreNotFound(long id) {
        throw new GenreNotFoundException("Genre with id '" + id + "' not exist");
    }

}

