package org.library.page;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.GenreNotFoundException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
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
public class BookPageController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookPageController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
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
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
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

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute(value = "book") Book book,
                          @ModelAttribute(value = "author") Author author) {
        final String genreName = book.getGenre().getName();
        final String authorName = author.getName();
        final Author authorToUpdate = authorRepository.findByName(authorName)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with name: %s not found", authorName)));
        authorToUpdate.addBook(bookRepository.save(new Book(book.getTitle(), genreRepository.findByName(genreName)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre with name %s not found", genreName))), authorToUpdate)));
        authorRepository.save(authorToUpdate);
        return "redirect:/";
    }

    @GetMapping("/author/new")
    public String newAuthorPage(Model model) {
        model.addAttribute("author", new Author());
        return "newauthor";
    }

    @PostMapping("/addAuthor")
    public String addBook(@ModelAttribute(value = "author") Author author) {
        author.setName(author.getName());
        authorRepository.save(author);
        return "redirect:/";
    }

    @PostMapping("/book/{id}/update")
    public String updateBook(@PathVariable("id") String id, @ModelAttribute(value = "book") Book book, Model model) {
        final Book bookFromRepo = bookRepository.findById(id).orElseThrow();
        bookFromRepo.setTitle(book.getTitle());
        bookFromRepo.setGenre(genreRepository.findByName(book.getGenre().getName()).orElseThrow());
        bookRepository.save(bookFromRepo);
        return "redirect:/book/{id}/";
    }

    @PostMapping("/author/{id}/update")
    public String updateAuthor(@PathVariable("id") String id, @ModelAttribute(value = "author") Author author, Model model) {
        final Author authorFromRepo = authorRepository.findById(id).orElseThrow();
        authorFromRepo.setName(author.getName());
        authorRepository.save(authorFromRepo);
        return "redirect:/author/{id}";
    }

    @GetMapping("/author/{id}/delete")
    public String deleteAuthor(@PathVariable("id") String id) {
        bookRepository.deleteAll(bookRepository.findAllByAuthor(authorRepository.findById(id).orElseThrow()));
        authorRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/author/{id}")
    public String authorPage(@PathVariable("id") String id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);
        return "author";
    }

    @PostMapping("/book/{id}/comment/add")
    public String addBook(@PathVariable("id") String id, Comment comment) {
        final Book book = bookRepository.findById(id).orElseThrow();
        book.getComments().add(commentRepository.save(new Comment((comment.getText()), LocalDate.now())));
        bookRepository.save(book);
        return "redirect:/book/{id}";
    }

    @GetMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}
