package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryTest {

    private static final String EXISTING_GENRE_NAME = "Other";

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @Order(1)
    void shouldfindAll() {
        assertThat(genreRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void shouldFindById() {
        Genre genre = genreRepository.save(new Genre("New genre 1"));
        assertThat(genreRepository.findById(genre.getId()).get()).isEqualTo(genre);
    }

    @Test
    @Order(3)
    void shouldDeleteById() {
        Genre genre = genreRepository.save(new Genre("New genre 2"));
        genreRepository.deleteById(genre.getId());
        assertThat(genreRepository.findAll()).doesNotContain(genre);
    }

    @Test
    @Order(4)
    void shouldNotAddDuplicatedGenreName() {
        assertThrows(DuplicateKeyException.class, () -> genreRepository.save(new Genre(EXISTING_GENRE_NAME)));
    }
}