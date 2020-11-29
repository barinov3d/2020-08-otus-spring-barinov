package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    void shouldFindById() {
        Comment comment = new Comment(bookRepository.findById(1L).get(), "New comment text 1", LocalDate.now());
        commentRepository.save(comment);
        assertThat(commentRepository.findById("1").get()).isEqualTo(comment);
    }

    @Test
    @Order(2)
    void shouldDeleteById() {
        Comment comment = new Comment(bookRepository.findById(1L).get(), "New comment text 2", LocalDate.now());
        commentRepository.save(comment);
        commentRepository.deleteById("2");
        final List<Comment> comments = commentRepository.findAll();
        assertThat(comments).doesNotContain(comment);
    }

    @Test
    @Order(3)
    void shouldfindAll() {
        System.out.println(commentRepository.findAll());
        assertThat(commentRepository.findAll().size()).isEqualTo(1);
    }
}