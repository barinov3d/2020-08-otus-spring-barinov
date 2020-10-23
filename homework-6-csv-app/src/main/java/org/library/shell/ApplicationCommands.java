package org.library.shell;

import lombok.AllArgsConstructor;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.GenreRepository;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Optional;

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

    @ShellMethod(value = "add -title 'book title' -comment -author 'author name' -genre 'genre name'", key = {"add"})
    public void add(@ShellOption(value = {"-title"}) String title,
                    @ShellOption(value = {"-comment"}, defaultValue = "") String comment,
                    @ShellOption(value = {"-author"}) String authorName,
                    @ShellOption(value = {"-genre"}) String genreName) {
        final Optional<Author> authorOptional = authorRepository.findByName(authorName);
        if (authorOptional.isEmpty()) {
            authorRepository.save(new Author(0, authorName));
        }
        final Optional<Genre> genreOptional = genreRepository.findByName(genreName);
        if (genreOptional.isEmpty()) {
            genreRepository.save(new Genre(0, genreName));
        }
        bookRepository.save(new Book(0, title, comment, authorRepository.findByName(authorName).get(),
                genreRepository.findByName(genreName).get()));
    }

    @ShellMethod(value = "find book by id 'find -id 1'", key = {"find"})
    public Book findById(@ShellOption(value = {"-id"}) long id) {
        return bookRepository.findById(id);
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

    @ShellMethod(value = "find all author books", key = {"findauthorbooks"})
    public List<Book> findAllAuthorBooks(@ShellOption(value = {"-name"}) String name) {
        return bookRepository.findAllAuthorBooks(authorRepository.findByName(name).get());
    }

    @ShellMethod(value = "updatet -id 'id' 'book title'", key = {"updatet"})
    public void updateTitleById(@ShellOption(value = {"-id"}) long id, String title) {
        bookRepository.updateTitleById(id, title);
    }

    @ShellMethod(value = "updatec -id 'id' 'book comment'", key = {"updatec"})
    public void updateCommentById(@ShellOption(value = {"-id"}) long id, String comment) {
        bookRepository.updateCommentById(id, comment);
    }

    @ShellMethod(value = "'delete -id'", key = {"delete"})
    public void deleteById(@ShellOption(value = {"-id"}) long id) {
        bookRepository.deleteById(id);
    }

}
