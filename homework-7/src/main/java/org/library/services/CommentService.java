package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Comment;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.services.exceptions.BookNotFoundException;
import org.library.services.exceptions.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    /**
     * Creates comment
     */
    public void createComment(String commentText, long bookId) {
        Comment comment = new Comment(0, bookRepository
                .findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id '" + bookId + "' not exist")), commentText, LocalDate.now());
        commentRepository.save(comment);
    }

    /**
     * Finds by id
     */
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id '" + id + "' not exist"));
    }

    /**
     * Finds all comments
     */
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    /**
     * Updates comment text
     */
    public void updateName(long id, String text) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id '" + id + "' not exist"));
        comment.setText(text);
        commentRepository.save(comment);
    }

    /**
     * Deletes comment by id
     */
    public void deleteComment(long id) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id '" + id + "' not exist"));
        commentRepository.delete(comment);
    }

    private void commentNotFound(long id) {
        throw new CommentNotFoundException("Comment with id '" + id + "' not exist");
    }

    private void bookNotFound(long id) {
        throw new BookNotFoundException("Book with id '" + id + "' not exist");
    }
}

