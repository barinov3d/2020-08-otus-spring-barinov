package org.library.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.domain.Book;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookDaoJdbcTest {
    private static final int EXPECTED_START_BOOK_COUNT = 3;
    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "Thinking in java";
    private static final String NEW_BOOK_TITLE = "Harry Potter";
    private static final int NEW_BOOK_ID = 4;

    @Autowired
    private BookDaoJdbc bookRepository;

    @Test
    @Order(1)
    void shouldFindAllBooks() {
        final List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(EXPECTED_START_BOOK_COUNT);
    }

    @Test
    @Order(2)
    void shouldReturnCorrectCount() {
        assertThat(bookRepository.count()).isEqualTo(EXPECTED_START_BOOK_COUNT);
    }

    @Test
    @Order(3)
    void shouldInsertBook() {
        bookRepository.insert(new Book(NEW_BOOK_ID, NEW_BOOK_TITLE, 4, 2));
        assertThat(bookRepository.findById(NEW_BOOK_ID)).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("title", NEW_BOOK_TITLE);
    }

    @Test
    @Order(4)
    void shouldFindBookById() {
        Optional<Book> book = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(book).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("title", FIRST_BOOK_TITLE);
    }

    @Test
    @Order(5)
    void shouldUpdateTitle() {
        final long id = bookRepository.findById(FIRST_BOOK_ID).get().getId();
        final String title = bookRepository.findById(FIRST_BOOK_ID).get().getTitle();
        final String updatedTitle = title + "_updated";
        bookRepository.updateTitle(new Book(id, updatedTitle, 4, 4));
        assertThat(bookRepository.findById(FIRST_BOOK_ID)).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("title", updatedTitle);
    }

    @Test
    @Order(6)
    void shouldDeleteById() {
        assertThat(bookRepository.findById(FIRST_BOOK_ID)).isNotEmpty();
        final int startSize = bookRepository.count();
        bookRepository.deleteById(FIRST_BOOK_ID);
        assertThat(bookRepository.findById(FIRST_BOOK_ID)).isEmpty();
    }
}