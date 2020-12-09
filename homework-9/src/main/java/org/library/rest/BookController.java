package org.library.rest;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.GenreNotFoundException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
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

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book/{id}")
    public String bookPage(@PathVariable("id") String id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        Author author = authorRepository.findAuthorByBook(book);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("author", author);
        model.addAttribute("genres", genres);
        return "book";
    }

    @GetMapping("/book/new")
    public String newBookPage(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("book", new Book());
        model.addAttribute("author", new Author());
        return "newbook";
    }

    @PostMapping("/book/{id}/update")
    public String updateBook(@PathVariable("id") String id, @ModelAttribute(value = "book") Book book) {
        final Book bookFromRepo = bookRepository.findById(id).orElseThrow();
        bookFromRepo.setTitle(book.getTitle());
        bookFromRepo.setGenre(genreRepository.findByName(book.getGenre().getName()).orElseThrow());
        bookRepository.save(bookFromRepo);
        return "redirect:/book/{id}";
    }

    @GetMapping("/author/{id}")
    public String authorPage(@PathVariable("id") String id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);
        return "author";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute(value = "book") Book book,
                          @ModelAttribute(value = "author") Author author){
        final String genreName = book.getGenre().getName();
        final String authorName = author.getName();
        final Author authorToUpdate = authorRepository.findByName(authorName)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with name: %s not found", authorName)));
        authorToUpdate.addBook(bookRepository.save(new Book(book.getTitle(), genreRepository.findByName(genreName)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre with name %s not found", genreName))), authorToUpdate)));
        authorRepository.save(authorToUpdate);
        return "redirect:/";
    }

    @GetMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable("id") String id){
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}
