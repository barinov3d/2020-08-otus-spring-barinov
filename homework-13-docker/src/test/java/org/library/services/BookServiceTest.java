package org.library.services;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {
    private static final long STARTED_BOOKS_COUNT = 3;
    private static final String NEW_BOOK_TITLE = "Thinking in java";
    private static final String NEW_BOOK_GENRE_NAME = "Other";
    private final String NEW_AUTHOR_NAME = "Bruce Eckel";
    private Book newBook;
    private Book newBook2;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private AuthorServiceImpl authorService;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(bookRepository, authorRepository);
        this.authorService = new AuthorServiceImpl(bookRepository, authorRepository);
    }

    @Test
    void shouldReturnTotalCount() {
        assertThat(bookService.count()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    void shouldFindAll() {
        assertThat(bookService.findAll().size()).isEqualTo(STARTED_BOOKS_COUNT);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldAddSeveralBooks() {
        newBook = new Book(NEW_BOOK_TITLE, new Genre(NEW_BOOK_GENRE_NAME), authorService.findByName(NEW_AUTHOR_NAME));
        newBook2 = new Book(NEW_BOOK_TITLE + "2", new Genre(NEW_BOOK_GENRE_NAME), authorService.findByName(NEW_AUTHOR_NAME));
        Book book1 = bookService.save(newBook);
        Book book2 = bookService.save(newBook2);
        assertThat(bookService.findById(book1.getId())).isEqualTo(book1);
        assertThat(bookService.findById(book2.getId())).isEqualTo(book2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldFindAllByAuthors() {
        final Author author = authorService.findByName(NEW_AUTHOR_NAME);
        author.addBook(bookService.save(new Book("Animals", new Genre("My new genre"), author)));
        authorService.save(author);
        assertThat(bookService.findAllByAuthor(author).size()).isEqualTo(2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldFindById() {
        newBook = new Book(NEW_BOOK_TITLE, new Genre(NEW_BOOK_GENRE_NAME), authorService.findByName(NEW_AUTHOR_NAME));
        Book book1 = bookService.save(newBook);
        assertThat(bookService.findById(book1.getId())).isEqualTo(book1);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateTitle() {
        final String expectedTitle = NEW_BOOK_TITLE + " updated";
        final Book book = bookService.findBookByAuthorAndTitle(authorService.findByName(NEW_AUTHOR_NAME), NEW_BOOK_TITLE);
        book.setTitle(expectedTitle);
        bookService.save(book);
        assertThat(bookService.findById(book.getId())).isEqualTo(book);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        newBook = new Book(NEW_BOOK_TITLE, new Genre(NEW_BOOK_GENRE_NAME), authorService.findByName(NEW_AUTHOR_NAME));
        Book book1 = bookService.save(newBook);
        bookService.deleteById(book1.getId());
        final List<Book> books = bookService.findAll();
        assertThat(books).doesNotContain(newBook);
    }

}