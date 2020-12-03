package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Author;
import org.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AuthorRepositoryTest {

    private static final String EXISTING_AUTHOR_NAME = "Zed A. Shaw";
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
    @Order(2)
    void shouldFindAuthorBooks() {
        Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME).get();
        List<Book> books = author.getBooks();
        assertThat(books.get(0))
                .isEqualTo(bookRepository.findBookByAuthorAndTitle(author, "Learn Python the Hard Way").get());
    }

    @Test
    @Order(3)
    void shouldUpdateNameById() {
        var existingAuthorId = authorRepository.findByName(EXISTING_AUTHOR_NAME).get().getId();
        final String expectedName = EXISTING_AUTHOR_NAME + " updated";
        final Author author = authorRepository.findByName(EXISTING_AUTHOR_NAME).get();
        author.setName(expectedName);
        authorRepository.save(author);
        final Author actualAuthor = authorRepository.findById(existingAuthorId).get();
        assertThat(actualAuthor.getName()).isEqualTo(expectedName);
    }

    @Test
    @Order(4)
    void shouldFindById() {
        Author authorFromRepo = authorRepository.save(new Author(EXISTING_AUTHOR_NAME + 2));
        final String authorId = authorFromRepo.getId();
        assertThat(authorRepository.findById(authorId).get()).isEqualTo(authorFromRepo);
    }

    @Test
    @Order(5)
    void shouldDeleteById() {
        final String authorName = EXISTING_AUTHOR_NAME + 2;
        final Author authorFromRepo = authorRepository.save(new Author(authorName));
        authorRepository.deleteById(authorFromRepo.getId());
        assertThat(authorRepository.findAll()).doesNotContain(authorFromRepo);
    }


    @Test
    @Order(6)
    void shouldNotAddDuplicatedAuthorName() {
        assertThrows(DuplicateKeyException.class, () ->
                authorRepository.save(new Author(EXISTING_AUTHOR_NAME)));
    }
}