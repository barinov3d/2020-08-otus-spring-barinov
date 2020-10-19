package org.library.shell;

import lombok.AllArgsConstructor;
import org.library.dao.AuthorDao;
import org.library.dao.BookDao;
import org.library.dao.GenreDao;
import org.library.domain.Author;
import org.library.domain.Book;
import org.library.domain.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ApplicationCommands {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @ShellMethod(value = "total book count", key = {"count"})
    public String count() {
        return "Books total count is: " + bookDao.count();
    }

    @ShellMethod(value = "add -title 'book title' -comment -author 'author name' -genre 'genre name'", key = {"add"})
    public void add(@ShellOption(value = {"-title"}) String title,
                    @ShellOption(value = {"-comment"}, defaultValue = "") String comment,
                    @ShellOption(value = {"-author"}) String authorName,
                    @ShellOption(value = {"-genre"}) String genreName) {
        final Optional<Author> authorOptional = authorDao.findByName(authorName);
        if (authorOptional.isEmpty()) {
            authorDao.save(new Author(0, authorName));
        }
        final Optional<Genre> genreOptional = genreDao.findByName(genreName);
        if (genreOptional.isEmpty()) {
            genreDao.save(new Genre(0, genreName));
        }
        bookDao.save(new Book(0, title, comment, authorDao.findByName(authorName).get(), genreDao.findByName(genreName).get()));
    }

    @ShellMethod(value = "find book by id 'find -id 1'", key = {"find"})
    public Book findById(@ShellOption(value = {"-id"}) long id) {
        return bookDao.findById(id);
    }

    @ShellMethod(value = "find all books", key = {"findb"})
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @ShellMethod(value = "find all book authors", key = {"finda"})
    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    @ShellMethod(value = "find all book genres", key = {"findg"})
    public List<Genre> findAllGenres() {
        return genreDao.findAll();
    }

    @ShellMethod(value = "updatet -id 'id' 'book title'", key = {"updatet"})
    public void updateTitleById(@ShellOption(value = {"-id"}) long id, String title) {
        bookDao.updateTitleById(id, title);
    }

    @ShellMethod(value = "updatec -id 'id' 'book comment'", key = {"updatec"})
    public void updateCommentById(@ShellOption(value = {"-id"}) long id, String comment) {
        bookDao.updateCommentById(id, comment);
    }

    @ShellMethod(value = "'delete -id'", key = {"delete"})
    public void deleteById(@ShellOption(value = {"-id"}) long id) {
        bookDao.deleteById(id);
    }

}
