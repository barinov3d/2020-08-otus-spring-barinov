package org.library.controllers;

import lombok.extern.slf4j.Slf4j;
import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.DuplicateAuthorBookException;
import org.library.exceptions.DuplicateAuthorNameException;
import org.library.exceptions.DuplicateGenreNameException;
import org.library.models.Author;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorNotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            DuplicateAuthorBookException.class,
            DuplicateGenreNameException.class,
            DuplicateAuthorNameException.class
    })
    public ModelAndView handleDuplicated(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("409");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
