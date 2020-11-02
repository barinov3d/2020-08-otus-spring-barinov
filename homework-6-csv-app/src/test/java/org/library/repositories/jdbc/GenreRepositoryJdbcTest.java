package org.library.repositories.jdbc;

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
class GenreRepositoryJdbcTest {

    private static final String EXISTING_GENRE_NAME = "Other";
    private static final Long EXISTING_GENRE_ID = 2L;

    @Autowired
    private GenreRepositoryJdbc genreRepositoryJdbc;

    @Test
    @Order(1)
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DataIntegrityViolationException.class, () -> genreRepositoryJdbc.save(new Genre(0, EXISTING_GENRE_NAME)));
    }


    @Test
    @Order(2)
    void shouldUpdateNameById() {
        final String expectedName = EXISTING_GENRE_NAME + " updated";
        genreRepositoryJdbc.updateNameById(EXISTING_GENRE_ID, expectedName);
        final Genre actualGenre = genreRepositoryJdbc.findById(EXISTING_GENRE_ID);
        assertThat(actualGenre.getName()).isEqualTo(expectedName);
    }

    @Test
    @Order(3)
    void shouldFindById() {
        Genre newGenre = new Genre(2L, EXISTING_GENRE_NAME + 1);
        genreRepositoryJdbc.save(newGenre);
        assertThat(genreRepositoryJdbc.findById(2L)).isEqualTo(newGenre);
    }

    @Test
    @Order(4)
    void shouldDeleteById() {
        Genre newGenre = new Genre(3L, EXISTING_GENRE_NAME + 2);
        genreRepositoryJdbc.save(newGenre);
        genreRepositoryJdbc.deleteById(4L);
        final List<Genre> genres = genreRepositoryJdbc.findAll();
        assertThat(genres).doesNotContain(newGenre);
    }
}