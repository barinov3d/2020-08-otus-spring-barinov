package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookRepositoryTest {
    private static final long STARTED_BOOKS_COUNT = 3;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final String NEW_BOOK_GENRE_NAME = "Other";
    private final String NEW_AUTHOR_NAME = "Bruce Eckel";
    private final Book newBook = new Book(NEW_BOOK_TITLE, new Genre(NEW_BOOK_GENRE_NAME));
    private final Book newBook2 = new Book(NEW_BOOK_TITLE + "2", new Genre(NEW_BOOK_GENRE_NAME));

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldReturnTotalCount() {
        assertThat(bookRepository.count()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    void shouldFindAll() {
        assertThat(bookRepository.findAll().size()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldAddSeveralBooks() {
        Author author = authorRepository.findByName(NEW_AUTHOR_NAME).orElseThrow(AssertionError::new);
        newBook.setAuthor(author);
        newBook2.setAuthor(author);
        Book book1 = bookRepository.save(newBook);
        Book book2 = bookRepository.save(newBook2);
        assertThat(bookRepository.findById(book1.getId())).get().isEqualTo(book1);
        assertThat(bookRepository.findById(book2.getId())).get().isEqualTo(book2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldFindAllByAuthors() {
        assertThat(bookRepository.findAllByAuthor(authorRepository.findByName(NEW_AUTHOR_NAME).get()).size()).isEqualTo(2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldFindById() {
        Book book1 = bookRepository.save(newBook);
        assertThat(bookRepository.findById(book1.getId()).get()).isEqualTo(book1);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateTitle() {
        final String expectedTitle = NEW_BOOK_TITLE + " updated";
        final Book book = bookRepository.findBookByAuthorAndTitle(authorRepository.findByName(NEW_AUTHOR_NAME).get(), NEW_BOOK_TITLE).get();
        book.setTitle(expectedTitle);
        bookRepository.save(book);
        assertThat(bookRepository.findById(book.getId()).get()).isEqualTo(book);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        Book book1 = bookRepository.save(newBook);
        bookRepository.deleteById(book1.getId());
        final List<Book> books = bookRepository.findAll();
        assertThat(books).doesNotContain(newBook);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldAlsoUpdateAuthorWhenBookAdded() {
        newBook.setAuthor(authorRepository.findByName(NEW_AUTHOR_NAME).orElseThrow(AssertionError::new));
        bookRepository.save(newBook);
        final Author author = authorRepository.findByName(NEW_AUTHOR_NAME).get();
        System.out.println(author.getBooks());
        assertThat(author.getBooks().size()).isEqualTo(2);
    }

}