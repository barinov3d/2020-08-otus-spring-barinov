package org.library.shell;

import lombok.AllArgsConstructor;
import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.BookNotFoundException;
import org.library.exceptions.GenreNotFoundException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.library.repositories.CommentRepository;
import org.library.repositories.GenreRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDate;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class ApplicationCommands {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    /**
     * Creates book
     */
    @ShellMethod(value = "book --create -title 'My title' -genre_name 'Other' -author_name 'Bruce Eckel'",
            key = {"book --create"})
    public void createBook(@ShellOption(value = {"-title"}) String title,
                           @ShellOption(value = {"-genre_name"}) String genreName,
                           @ShellOption(value = {"-author_name"}) String authorName
    ) {
        final Book book = bookRepository.save(new Book(title,
                genreRepository.findByName(genreName)
                        .orElseThrow(() ->
                                new GenreNotFoundException(String.format("Genre with name %s not found", genreName)))));
        final Author author = authorRepository.findByName(authorName).orElseThrow(() ->
                new AuthorNotFoundException(String.format("Author with name %s not found", authorName)));
        author.addBook(book);
        authorRepository.save(author);
    }

    /**
     * Finds by id
     */
    @ShellMethod(value = "book --find -id 1", key = {"book --find"})
    public Book findBookById(@ShellOption(value = {"-id"}) String id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new GenreNotFoundException(String.format("Book with id %s not found", id)));
    }

    /**
     * Finds by author and book title
     */
    @ShellMethod(value = "book --find --at -author_name 'Bruce Eckel' -title 'Thinking in java'", key = {"book --find --at"})
    public Book findBookByAuthorAndTitle(@ShellOption(value = {"-author_name"}) String authorName, @ShellOption(value = {"-title"}) String title) {
        return bookRepository.findBookByAuthorAndTitle(authorRepository.findByName(authorName)
                        .orElseThrow(() ->
                                new AuthorNotFoundException(String.format("Author with name %s not found", authorName)))
                , title).orElseThrow(() ->
                new BookNotFoundException(String.format("Book with title %s not found", title)));
    }

    /**
     * Finds all books
     */
    @ShellMethod(value = "book --find --all", key = {"book --find --all"})
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Updates book title
     */
    @ShellMethod(value = "book --update -id 1 -title 'Updated title'", key = {"book --update"})
    public void updateTitle(@ShellOption(value = {"-id"}) String id) {
        final Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(String.format("Book with id %s not found", id)));
        bookRepository.save(book);
    }

    /**
     * Find all author books
     */
    @ShellMethod(value = "book --delete -id 1 ", key = {"book --delete"})
    public void deleteBook(@ShellOption(value = {"-id"}) String id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(String.format("Book with id %s not found", id))));
    }

    @ShellMethod(value = "book --find --all --by_author -author 'Bruce Eckel'", key = {"book --find --all --by_author"})
    public List<Book> findAllAuthorBooks(@ShellOption(value = {"-author"}) String authorName) {
        return bookRepository.findAllByAuthor(authorRepository.findByName(authorName).orElseThrow(() ->
                new AuthorNotFoundException(String.format("Author with id %s not found", authorName))));
    }

    /**
     * Creates author
     */
    @ShellMethod(value = "author --create -name 'author name'",
            key = {"author --create"})
    public void createAuthor(@ShellOption(value = {"-name"}) String authorName) {
        authorRepository.save(new Author(authorName));
    }

    /**
     * Finds all authors
     */
    @ShellMethod(value = "author --find --all", key = {"author --find --all"})
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Creates genre
     */
    @ShellMethod(value = "genre --create -name 'genre name'",
            key = {"genre --create"})
    public void createGenre(@ShellOption(value = {"-name"}) String genreName) {
        genreRepository.save(new Genre(genreName));
    }

    /**
     * Finds all genres
     */
    @ShellMethod(value = "genre --find --all", key = {"genre --find --all"})
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Creates comment
     */
    @ShellMethod(value = "comment --create -text 'comment text'",
            key = {"comment --create"})
    public void createComment(@ShellOption(value = {"-text"}) String commentText, @ShellOption(value = {"-book_id"}) String bookId) {
        commentRepository.save(new Comment(commentText, LocalDate.now()));
    }

    /**
     * Finds all comments
     */
    @ShellMethod(value = "comment --find --all", key = {"comment --find --all"})
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

}
