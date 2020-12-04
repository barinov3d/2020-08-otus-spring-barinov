package org.library.repositories;

import org.junit.jupiter.api.*;
import org.library.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
    void shouldfindAll() {
        assertThat(commentRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void shouldFindById() {
        Comment comment = commentRepository.save(new Comment("New comment text 1", LocalDate.now()));
        assertThat(commentRepository.findById(comment.getId()).get()).isEqualTo(comment);
    }

    @Test
    @Order(3)
    void shouldDeleteById() {
        Comment comment = commentRepository.save(new Comment("New comment text 2", LocalDate.now()));
        commentRepository.deleteById(comment.getId());
        assertThat(commentRepository.findAll()).doesNotContain(comment);
    }
}