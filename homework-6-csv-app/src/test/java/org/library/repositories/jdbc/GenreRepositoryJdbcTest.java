package org.library.repositories.jdbc;

import org.junit.jupiter.api.*;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryJdbcTest {

    private static final String EXISTING_GENRE_NAME = "Other";

    @Autowired
    private GenreRepositoryJdbc genreRepositoryJdbc;

    @Test
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DataIntegrityViolationException.class, () -> genreRepositoryJdbc.save(new Genre(0, EXISTING_GENRE_NAME)));
    }
}