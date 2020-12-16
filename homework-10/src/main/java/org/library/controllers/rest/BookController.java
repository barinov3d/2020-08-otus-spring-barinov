package org.library.controllers.rest;

import org.library.models.Book;
import org.library.models.Comment;
import org.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}/comments")
    public List<Comment> getBookComments(@PathVariable("id") String id) {
        final Book book = bookService.findById(id);
        return book.getComments();
    }

}
