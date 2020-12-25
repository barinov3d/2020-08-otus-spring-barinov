package org.library.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.library.exceptions.BookNotFoundException;
import org.library.exceptions.DuplicateAuthorBookException;
import org.library.exceptions.DuplicateAuthorNameException;
import org.library.exceptions.DuplicateGenreNameException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.CommentService;
import org.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class BookPageController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") String id, Model model) {
        Book book = bookService.findById(id);
        Author author = authorService.findAuthorByBook(book);
        List<Genre> genres = genreService.findAll();
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        model.addAttribute("book", book);
        model.addAttribute("author", author);
        model.addAttribute("genres", genres);
        return "book";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
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

    @GetMapping("/book")
    public String newBookPage(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("book", new Book());
        model.addAttribute("author", new Author());
        return "newbook";
    }

    @PostMapping("/book")
    public String addBook(@ModelAttribute(value = "book") Book book,
                          @ModelAttribute(value = "author") Author author) {
        final String genreName = book.getGenre().getName();
        final String authorName = author.getName();
        final Author authorToUpdate = authorService.findByName(authorName);
        authorToUpdate.addBook(bookService.save(new Book(book.getTitle(), genreService.findByName(genreName), authorToUpdate)));
        authorService.save(authorToUpdate);
        return "redirect:/";
    }

    @PostMapping("/book/{id}/update")
    public String updateBook(@PathVariable("id") String id, @ModelAttribute(value = "book") Book book, Model model) {
        final Book bookFromRepo = bookService.findById(id);
        bookFromRepo.setTitle(book.getTitle());
        bookFromRepo.setGenre(genreService.findByName(book.getGenre().getName()));
        bookService.save(bookFromRepo);
        return "redirect:/book/{id}/";
    }

    @PostMapping("/book/{id}/comment/")
    public String addBook(@PathVariable("id") String id, Comment comment) {
        final Book book = bookService.findById(id);
        book.getComments().add(commentService.save(new Comment((comment.getText()), LocalDate.now())));
        bookService.save(book);
        return "redirect:/book/{id}";
    }

    @GetMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable("id") String id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
