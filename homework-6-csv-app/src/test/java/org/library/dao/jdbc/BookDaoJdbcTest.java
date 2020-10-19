package org.library.dao.jdbc;

import org.junit.jupiter.api.*;
import org.library.domain.Author;
import org.library.domain.Book;
import org.library.domain.Genre;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookDaoJdbcTest {

    private static final long STARTED_BOOKS_COUNT = 3;
    private static final long NEW_BOOK_ID = 4;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final String NEW_BOOK_COMMENT = "Comment";
    private static final long NEW_BOOK_AUTHOR_ID = 4;
    private static final String NEW_BOOK_AUTHOR_NAME = "Super Author";
    private static final long NEW_BOOK_GENRE_ID = 2;
    private static final String NEW_BOOK_GENRE_NAME = "Other";
    private final Book newBook = new Book(NEW_BOOK_ID, NEW_BOOK_TITLE, NEW_BOOK_COMMENT,
            new Author(NEW_BOOK_AUTHOR_ID, NEW_BOOK_AUTHOR_NAME),
            new Genre(NEW_BOOK_GENRE_ID, NEW_BOOK_GENRE_NAME));
    @Autowired
    private BookDaoJdbc bookRepository;

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
        bookRepository.save(newBook);
        assertThat(bookRepository.findById(NEW_BOOK_ID)).isEqualTo(newBook);
    }

    @Test
    @Order(4)
    void shouldFindById() {
        assertThat(bookRepository.findById(NEW_BOOK_ID)).isEqualTo(newBook);
    }

    @Test
    @Order(5)
    void shouldUpdateTitle() {
        final String expectedTitle = NEW_BOOK_TITLE + " updated";
        bookRepository.updateTitleById(NEW_BOOK_ID, expectedTitle);
        final Book actualBook = bookRepository.findById(NEW_BOOK_ID);
        assertThat(actualBook.getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @Order(6)
    void shouldUpdateComment() {
        final String expectedComment = NEW_BOOK_COMMENT + " updated";
        bookRepository.updateCommentById(NEW_BOOK_ID, expectedComment);
        final Book actualBook = bookRepository.findById(NEW_BOOK_ID);
        assertThat(actualBook.getComment()).isEqualTo(expectedComment);
    }

    @Test
    @Order(8)
    void shouldDeleteById() {
        bookRepository.deleteById(NEW_BOOK_ID);
        assertThat(bookRepository.findAll()).doesNotContain(newBook);
    }

}