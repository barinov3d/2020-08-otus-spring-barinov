package org.library.dao.jdbc;

import org.junit.jupiter.api.*;
import org.library.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreDaoJdbcTest {

    private static final String EXISTING_GENRE_NAME = "Other";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DataIntegrityViolationException.class, () -> genreDaoJdbc.save(new Genre(0, EXISTING_GENRE_NAME)));
    }
}