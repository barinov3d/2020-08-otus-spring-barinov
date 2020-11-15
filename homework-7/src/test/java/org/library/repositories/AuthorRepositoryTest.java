package org.library.repositories.jdbc;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorRepositoryTest {

    private static final String EXISTING_AUTHOR_NAME = "Zed A. Shaw";
    private static final Long EXISTING_AUTHOR_ID = 2L;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DataIntegrityViolationException.class, () ->
                authorRepository.save(new Author(0, EXISTING_AUTHOR_NAME, Collections.emptyList())));
    }

    @Test
    @Order(2)
    void shouldFindAuthorBooks() {
        Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME);
        List<Book> books = author.getBooks();
        assertThat(books).contains(bookRepository.findById(2L).get());
    }

    @Test
    @Order(3)
    void shouldUpdateNameById() {
        final String expectedName = EXISTING_AUTHOR_NAME + " updated";
        final Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME);
        author.setName(expectedName);
        authorRepository.save(author);
        final Author actualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID).get();
        assertThat(actualAuthor.getName()).isEqualTo(expectedName);
    }

    @Test
    @Order(4)
    void shouldFindById() {
        Author newAuthor = new Author(6L, EXISTING_AUTHOR_NAME + 2, null);
        authorRepository.save(newAuthor);
        assertThat(authorRepository.findById(6L).get()).isEqualTo(newAuthor);
    }

    @Test
    @Order(5)
    void shouldDeleteById() {
        Author newAuthor = new Author(6L, EXISTING_AUTHOR_NAME + 2, null);
        authorRepository.save(newAuthor);
        authorRepository.deleteById(6L);
        final List<Author> authors = authorRepository.findAll();
        assertThat(authors).doesNotContain(newAuthor);
    }

}