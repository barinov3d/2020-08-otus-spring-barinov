package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryTest {

    private static final String EXISTING_GENRE_NAME = "Other";
    private static final Long EXISTING_GENRE_ID = 2L;
    private static final int STARTED_GENRE_COUNT = 2;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @Order(0)
    void shouldfindAll() {
        assertThat(genreRepository.findAll().size()).isEqualTo(STARTED_GENRE_COUNT);
    }

    @Test
    @Order(1)
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DataIntegrityViolationException.class, () -> genreRepository.save(new Genre(0, EXISTING_GENRE_NAME)));
    }


    @Test
    @Order(2)
    void shouldUpdateNameById() {
        final String expectedName = EXISTING_GENRE_NAME + " updated";
        final Genre actualGenre = genreRepository.findByName(EXISTING_GENRE_NAME).get();
        actualGenre.setName(expectedName);
        genreRepository.save(actualGenre);
        final Genre actualAuthor = genreRepository.findById(EXISTING_GENRE_ID).get();
        assertThat(actualGenre.getName()).isEqualTo(expectedName);
    }


    @Test
    @Order(3)
    void shouldFindById() {
        Genre newGenre = new Genre(2L, EXISTING_GENRE_NAME + 1);
        genreRepository.save(newGenre);
        assertThat(genreRepository.findById(2L).get()).isEqualTo(newGenre);
    }

    @Test
    @Order(4)
    void shouldDeleteById() {
        Genre newGenre = new Genre(3L, EXISTING_GENRE_NAME + 2);
        genreRepository.save(newGenre);
        genreRepository.deleteById(4L);
        final List<Genre> genres = genreRepository.findAll();
        assertThat(genres).doesNotContain(newGenre);
    }
}