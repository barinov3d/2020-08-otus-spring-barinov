package org.library.rest;

import org.library.models.Book;
import org.library.models.Comment;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/book/{id}/comments")
    public List<Comment> getBookComments(@PathVariable("id") String id) {
        final Book book = bookRepository.findById(id).orElseThrow();
        return book.getComments();
    }

}
