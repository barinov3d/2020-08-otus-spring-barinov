package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
import org.library.services.exceptions.BookNotFoundException;
import org.library.services.exceptions.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    /**
     * Creates comment
     */
    public void createComment(String commentText, long bookId) {
        final Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            bookNotFound(bookId);
        }
        Comment comment = new Comment(0, optionalBook.get(), commentText, LocalDate.now());
        commentRepository.save(comment);
    }

    /**
     * Finds by id
     */
    public Comment findById(long id) {
        final Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            commentNotFound(id);
        }
        return optionalComment.get();
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
        final Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            commentNotFound(id);
        }
        final Comment comment = commentRepository.findById(id).get();
        comment.setText(text);
        commentRepository.save(comment);
    }

    /**
     * Deletes comment by id
     */
    public void deleteComment(long id) {
        final Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            commentNotFound(id);
            return;
        }
        final Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }

    private void commentNotFound(long id) {
        throw new CommentNotFoundException("Comment with id '" + id + "' not exist");
    }

    private void bookNotFound(long id) {
        throw new BookNotFoundException("Book with id '" + id + "' not exist");
    }
}

