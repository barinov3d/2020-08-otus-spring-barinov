package org.library.dao.jdbc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.domain.Author;
import org.library.domain.Book;
import org.library.domain.Genre;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookDaoJdbcTest {

    private static final int STARTED_BOOKS_COUNT = 3;
    private static final long NEW_BOOK_ID = 4;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final long NEW_BOOK_AUTHOR_ID = 4;
    private static final String NEW_BOOK_AUTHOR_NAME = "Super Author";
    private static final long NEW_BOOK_GENRE_ID = 2;
    private static final String NEW_BOOK_GENRE_NAME = "Other";

    @Autowired
    private BookDaoJdbc bookRepository;
    private final Book newBook = new Book(NEW_BOOK_ID,NEW_BOOK_TITLE,
            new Author(NEW_BOOK_AUTHOR_ID,NEW_BOOK_AUTHOR_NAME),
            new Genre(NEW_BOOK_GENRE_ID, NEW_BOOK_GENRE_NAME));

    @Test
    @Order(1)
    void shouldReturnTotalCount() {
        assertThat(bookRepository.count()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    @Order(2)
    void shouldFindAll() {
        assertThat(bookRepository.findAll().size()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    @Order(3)
    void shouldAdd() {
        bookRepository.add(newBook);
        assertThat(bookRepository.findById(NEW_BOOK_ID).get()).isEqualTo(newBook);
    }

    @Test
    @Order(4)
    void shouldFindById() {
        assertThat(bookRepository.findById(NEW_BOOK_ID).get()).isEqualTo(newBook);
    }

    @Test
    @Order(5)
    void shouldUpdate() {
        final String expectedTitle = NEW_BOOK_TITLE + " updated";
        final String expectedAuthorName = NEW_BOOK_AUTHOR_NAME + " updated";
        final String expectedGenreName = NEW_BOOK_GENRE_NAME + " updated";
        final Book updatedBook = new Book(NEW_BOOK_ID, expectedTitle,
                new Author(NEW_BOOK_AUTHOR_ID, expectedAuthorName),
                new Genre(NEW_BOOK_GENRE_ID, expectedGenreName));
        bookRepository.update(updatedBook);
        final Book actualBook = bookRepository.findById(NEW_BOOK_ID).get();
        assertThat(actualBook.getTitle()).isEqualTo(expectedTitle);
        assertThat(actualBook.getAuthor().getName()).isEqualTo(expectedAuthorName);
        assertThat(actualBook.getGenre().getName()).isEqualTo(expectedGenreName);
    }

    @Test
    @Order(6)
    void shouldDeleteById() {
        bookRepository.deleteById(NEW_BOOK_ID);
        assertThat(bookRepository.findAll()).doesNotContain(newBook);
    }

}