package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.exceptions.DuplicateGenreNameException;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryTest {

    private static final String EXISTING_GENRE_NAME = "Other";

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldfindAll() {
        assertThat(genreRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void shouldFindById() {
        Genre genre = genreRepository.save(new Genre("New genre 1"));
        assertThat(genreRepository.findById(genre.getId()).get()).isEqualTo(genre);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        Genre genre = genreRepository.save(new Genre("New genre 2"));
        genreRepository.deleteById(genre.getId());
        assertThat(genreRepository.findAll()).doesNotContain(genre);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DuplicateGenreNameException.class, () -> genreRepository.save(new Genre(EXISTING_GENRE_NAME)));
    }
}