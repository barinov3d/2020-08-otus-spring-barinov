package org.library.shell;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.repositories.jdbc.exceptions.BookNotFoundException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class ApplicationCommands {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @ShellMethod(value = "total book count", key = {"count"})
    public String count() {
        return "Books total count is: " + bookRepository.count();
    }

    @ShellMethod(value = "add -title 'book title' -comment 'my comment' -author 'Bruce Eckel' -genre 'Other'", key = {"add"})
    public void add(@ShellOption(value = {"-title"}) String title,
                    @ShellOption(value = {"-comment"}, defaultValue = "") String comment,
                    @ShellOption(value = {"-author"}) String authorName,
                    @ShellOption(value = {"-genre"}) String genreName) {
        final Book book = new Book(0, title, comment, authorRepository.findByName(authorName),
                genreRepository.findByName(genreName));
        bookRepository.save(book);
    }

    @ShellMethod(value = "find -id 1", key = {"find"})
    public Book findById(@ShellOption(value = {"-id"}) long id) {
        Book book = null;
        try {
            book = bookRepository.findById(id);
        } catch (BookNotFoundException e) {
            System.out.println("Book with id = " + id + " not found");
        }
        return book;
    }

    @ShellMethod(value = "find all books", key = {"findb"})
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @ShellMethod(value = "find all book authors", key = {"finda"})
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @ShellMethod(value = "find all book genres", key = {"findg"})
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @ShellMethod(value = "find all author books: findab -name 'Bruce Eckel'", key = {"findab"})
    public List<Book> findAllAuthorBooks(@ShellOption(value = {"-name"}) String name) {
        return bookRepository.findAll(authorRepository.findByName(name));
    }

    @ShellMethod(value = "updatet -id 'id' 'book title'", key = {"updatet"})
    public void updateTitleById(@ShellOption(value = {"-id"}) long id, String title) {
        bookRepository.updateTitleById(id, title);
    }

    @ShellMethod(value = "updatec -id 'id' 'book comment'", key = {"updatec"})
    public void updateCommentById(@ShellOption(value = {"-id"}) long id, String comment) {
        bookRepository.updateCommentById(id, comment);
    }

    @ShellMethod(value = "delete -id", key = {"delete"})
    public void deleteById(@ShellOption(value = {"-id"}) long id) {
        bookRepository.deleteById(id);
    }

}
