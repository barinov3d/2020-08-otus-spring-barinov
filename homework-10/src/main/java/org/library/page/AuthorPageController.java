package org.library.page;

import org.library.models.Author;
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

@Controller
public class AuthorPageController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AuthorPageController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/author")
    public String newAuthorPage(Model model) {
        model.addAttribute("author", new Author());
        return "newauthor";
    }

    @PostMapping("/author")
    public String addBook(@ModelAttribute(value = "author") Author author) {
        author.setName(author.getName());
        authorRepository.save(author);
        return "redirect:/";
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

}
