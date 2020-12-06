package org.library.rest;

import org.library.models.Author;
import org.library.models.Book;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book")
    public String editPage(@RequestParam("id") String id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        Author author = authorRepository.findAuthorByBook(book);
        model.addAttribute("book", book);
        model.addAttribute("author", author);
        return "book";
    }
}
