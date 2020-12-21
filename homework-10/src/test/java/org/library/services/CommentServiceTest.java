package org.library.services;

import org.junit.jupiter.api.*;
import org.library.models.Comment;
import org.library.repositories.CommentRepository;
import org.library.services.AuthorServiceImpl;
import org.library.services.BookServiceImpl;
import org.library.services.CommentService;
import org.library.services.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        this.commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    void shouldfindAll() {
        assertThat(commentService.findAll().size()).isEqualTo(5);
    }

    @Test
    void shouldFindById() {
        Comment comment = commentService.save(new Comment("New comment text 1", LocalDate.now()));
        assertThat(commentService.findById(comment.getId())).isEqualTo(comment);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteById() {
        Comment comment = commentService.save(new Comment("New comment text 2", LocalDate.now()));
        commentService.deleteById(comment.getId());
        assertThat(commentService.findAll()).doesNotContain(comment);
    }
}