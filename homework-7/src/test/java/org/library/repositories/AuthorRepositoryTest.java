package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
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
    private static final int STARTED_AUTHOR_COUNT = 4;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(0)
    void shouldfindAll() {
        assertThat(authorRepository.findAll().size()).isEqualTo(STARTED_AUTHOR_COUNT);
    }

    @Test
    @Order(1)
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DataIntegrityViolationException.class, () ->
                authorRepository.save(new Author(0, EXISTING_AUTHOR_NAME, Collections.emptyList())));
    }

    @Test
    @Order(2)
    void shouldFindAuthorBooks() {
        Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME).get();
        List<Book> books = author.getBooks();
        assertThat(books.get(0).getTitle()).isEqualTo(bookRepository.findById(EXISTING_AUTHOR_ID).get().getTitle());
    }

    @Test
    @Order(3)
    void shouldUpdateNameById() {
        final String expectedName = EXISTING_AUTHOR_NAME + " updated";
        final Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME).get();
        author.setName(expectedName);
        authorRepository.save(author);
        final Author actualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID).get();
        assertThat(actualAuthor.getName()).isEqualTo(expectedName);
    }

    @Test
    @Order(4)
    void shouldFindById() {
        Author newAuthor = new Author(6L, EXISTING_AUTHOR_NAME + 2, Collections.emptyList());
        authorRepository.save(newAuthor);
        System.out.println(bookRepository.findAll());
        assertThat(authorRepository.findById(6L).get().getName()).isEqualTo(newAuthor.getName());
    }

    @Test
    @Order(5)
    void shouldDeleteById() {
        final List<Author> authorsStart = authorRepository.findAll();
        Author newAuthor = new Author(6L, EXISTING_AUTHOR_NAME + 2, Collections.emptyList());
        assertThat(authorsStart.stream().filter(a -> a.getId() == 6L).findFirst().get().getName()).isEqualTo(EXISTING_AUTHOR_NAME + 2);
        authorRepository.save(newAuthor);
        authorRepository.deleteById(6L);
        final List<Author> authorsEnd = authorRepository.findAll();
        assertThat(authorsEnd.stream().filter(a -> a.getId() == 6L).count()).isEqualTo(0);
    }

}