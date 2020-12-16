package org.library.controllers;

import org.library.models.Author;
import org.library.repositories.AuthorRepository;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorPageController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public AuthorPageController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public String newAuthorPage(Model model) {
        model.addAttribute("author", new Author());
        return "newauthor";
    }

    @PostMapping("/author")
    public String addBook(@ModelAttribute(value = "author") Author author) {
        author.setName(author.getName());
        authorService.save(author);
        return "redirect:/";
    }

    @PostMapping("/author/{id}/update")
    public String updateAuthor(@PathVariable("id") String id, @ModelAttribute(value = "author") Author author) {
        final Author authorFromRepo = authorService.findById(id);
        authorFromRepo.setName(author.getName());
        authorService.save(authorFromRepo);
        return "redirect:/author/{id}";
    }

    @GetMapping("/author/{id}/delete")
    public String deleteAuthor(@PathVariable("id") String id) {
        bookService.deleteAll(bookService.findAllByAuthor(authorService.findById(id)));
        authorService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/author/{id}")
    public String authorPage(@PathVariable("id") String id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "author";
    }

}
