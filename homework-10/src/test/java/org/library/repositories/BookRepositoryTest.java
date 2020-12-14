package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {
    private static final long STARTED_BOOKS_COUNT = 3;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final String NEW_BOOK_GENRE_NAME = "Other";
    private final String NEW_AUTHOR_NAME = "Bruce Eckel";
    private Book newBook;
    private Book newBook2;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        newBook = new Book(NEW_BOOK_TITLE, new Genre(NEW_BOOK_GENRE_NAME), authorRepository.findByName(NEW_AUTHOR_NAME)
                .orElseThrow(AssertionError::new));
        newBook2 = new Book(NEW_BOOK_TITLE + "2", new Genre(NEW_BOOK_GENRE_NAME), authorRepository.findByName(NEW_AUTHOR_NAME)
                .orElseThrow(AssertionError::new));
    }

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
        Book book1 = bookRepository.save(newBook);
        Book book2 = bookRepository.save(newBook2);
        assertThat(bookRepository.findById(book1.getId())).get().isEqualTo(book1);
        assertThat(bookRepository.findById(book2.getId())).get().isEqualTo(book2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldFindAllByAuthors() {
        final Author author = authorRepository.findByName(NEW_AUTHOR_NAME).get();
        author.addBook(bookRepository.save(new Book("Animals", new Genre("My new genre"), author)));
        authorRepository.save(author);
        assertThat(bookRepository.findAllByAuthor(author).size()).isEqualTo(2);
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

}