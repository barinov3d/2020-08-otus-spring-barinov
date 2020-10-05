package org.library.shell;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ApplicationCommands {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @ShellMethod(value = "'count'", key = {"count"})
    public String count() {
        return "Books total count is: " + bookDao.count();
    }

    @ShellMethod(value = "'add -title 'book title' -author 'author name' -genre 'genre name'", key = {"add"})
    public void add(@ShellOption(value = {"-title"}) String title,
                    @ShellOption(value = {"-author"}) String authorName,
                    @ShellOption(value = {"-genre"}) String genreName) {
        final Optional<Author> authorOptional = authorDao.findByName(authorName);
        if (authorOptional.isEmpty()) {
            authorDao.add(new Author(0, authorName));
        }
        final Optional<Genre> genreOptional = genreDao.findByName(genreName);
        if (genreOptional.isEmpty()) {
            genreDao.add(new Genre(0, genreName));
        }
        bookDao.add(new Book(0, title, authorDao.findByName(authorName).get(), genreDao.findByName(genreName).get()));
    }

    @ShellMethod(value = "='find -id 1'", key = {"find"})
    public Book findById(@ShellOption(value = {"-id"}) long id) {
        final Optional<Book> book = bookDao.findById(id);
        if(book.isEmpty()) {
            throw new RuntimeException("Book with id '" + id + "' not exist");
        }
        return book.get();
    }

    @ShellMethod(value = "'findbooks'", key = {"findbooks"})
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @ShellMethod(value = "'findauthors'", key = {"findauthors"})
    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    @ShellMethod(value = "'findgenres'", key = {"findgenres"})
    public List<Genre> findAllGenres() {
        return genreDao.findAll();
    }

    @ShellMethod(value = "'update -id 'id' -title 'book title' -author 'author name' -genre 'genre name''", key = {"update"})
    public void update(@ShellOption(value = {"-id"}) long id,
                       @ShellOption(value = {"-title"}) String title,
                       @ShellOption(value = {"-author"}) String authorName,
                       @ShellOption(value = {"-genre"}) String genreName) {
        bookDao.update(new Book(bookDao.findById(id).get().getId(), title,
                authorDao.findByName(authorName).get(),
                genreDao.findByName(genreName).get()));
    }

    @ShellMethod(value = "'delete -id'", key = {"delete"})
    public void deleteById(@ShellOption(value = {"-id"}) long id) {
        bookDao.deleteById(id);
    }

}
