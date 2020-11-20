package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookRepositoryTest {
    private static final long STARTED_BOOKS_COUNT = 3;
    private static final long NEW_BOOK_ID = 4;
    private static final long NEW_BOOK2_ID = 5;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final long NEW_BOOK_AUTHOR_ID = 4;
    private static final String NEW_BOOK_AUTHOR_NAME = "Super Author";
    private static final long NEW_BOOK_GENRE_ID = 2;
    private static final String NEW_BOOK_GENRE_NAME = "Other";
    private final Author newAuthor = new Author(NEW_BOOK_AUTHOR_ID, NEW_BOOK_AUTHOR_NAME, Collections.emptyList());
    private final Book newBook = new Book(NEW_BOOK_ID, NEW_BOOK_TITLE, null, newAuthor,
            new Genre(NEW_BOOK_GENRE_ID, NEW_BOOK_GENRE_NAME));
    private final Book newBook2 = new Book(NEW_BOOK2_ID, NEW_BOOK_TITLE + "2", null, newAuthor,
            new Genre(NEW_BOOK_GENRE_ID, NEW_BOOK_GENRE_NAME));
    private final Comment NEW_BOOK_COMMENT = new Comment(1L, newBook, "my important comment", LocalDate.now());

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

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
    void shouldAddSeveralBooks() {
        System.out.println(bookRepository.findAll());
        bookRepository.save(newBook);
        System.out.println(bookRepository.findAll());
        bookRepository.save(newBook2);
        assertThat(bookRepository.findById(NEW_BOOK_ID)).get().isEqualTo(newBook);
        assertThat(bookRepository.findById(NEW_BOOK2_ID)).get().isEqualTo(newBook2);
    }

    @Test
    @Order(3)
    void shouldFindAllByAuthors() {
        assertThat(bookRepository.findAllByAuthor(newAuthor).size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    void shouldFindById() {
        assertThat(bookRepository.findById(NEW_BOOK_ID).get()).isEqualTo(newBook);
    }

    @Test
    @Order(6)
    void shouldUpdateTitle() {
        final String expectedTitle = NEW_BOOK_TITLE + " updated";
        final Book book = bookRepository.findBookByAuthorAndTitle(authorRepository.findByName(NEW_BOOK_AUTHOR_NAME).get(), NEW_BOOK_TITLE).get();
        book.setTitle(expectedTitle);
        bookRepository.save(book);
        final Book actualBook = bookRepository.findById(NEW_BOOK_ID).get();
        assertThat(actualBook).isEqualTo(book);
    }

    @Test
    @Order(8)
    void shouldDeleteById() {
        bookRepository.save(newBook);
        bookRepository.deleteById(NEW_BOOK_ID);
        final List<Book> books = bookRepository.findAll();
        assertThat(books).doesNotContain(newBook);
    }

    @Test
    @Order(9)
    void shouldAlsoUpdateAuthorWhenBookAdded() {
        bookRepository.save(newBook);
        final Author author = authorRepository.findByName(NEW_BOOK_AUTHOR_NAME).get();
        System.out.println(author);
        assertThat(author.getBooks().size()).isEqualTo(2);
    }

}