package org.library.controllers;

import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.CommentService;
import org.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexPageController {
    private final BookService bookService;

    @Autowired
    public IndexPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "index";
    }

}
