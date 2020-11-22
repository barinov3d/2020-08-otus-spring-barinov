package org.library.shell;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.models.Book;
import org.library.models.Comment;
import org.library.models.Genre;
import org.library.repositories.AuthorRepository;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.CommentService;
import org.library.services.GenreService;
import org.library.services.exceptions.AuthorNotFoundException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class ApplicationCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final GenreService genreService;
    private final CommentService commentService;

    /**
     * Creates book
     */
    @ShellMethod(value = "book --create -title 'My title' " +
            "-author_name 'Bruce Eckel' -genre_name 'Other'",
            key = {"book --create"})
    public void createBook(@ShellOption(value = {"-title"}) String title,
                           @ShellOption(value = {"-author_name"}) String authorName,
                           @ShellOption(value = {"-genre_name"}) String genreName
    ) {
        bookService.createBook(title, authorName, genreName);
    }

    /**
     * Finds by id
     */
    @ShellMethod(value = "book --find -id 1", key = {"book --find"})
    public Book findBookById(@ShellOption(value = {"-id"}) long id) {
        return bookService.findById(id);
    }

    /**
     * Finds all books
     */
    @ShellMethod(value = "book --find --all", key = {"book --find --all"})
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    /**
     * Updates book title
     */
    @ShellMethod(value = "book --update -id 1 -title 'Updated title'", key = {"book --update"})
    public void updateTitle(@ShellOption(value = {"-id"}) long id,
                            @ShellOption(value = {"-title"}) String title) {
        bookService.updateTitle(id, title);
    }

    /**
     * Find all author books
     */
    @ShellMethod(value = "book --delete -id 1 ", key = {"book --delete"})
    public void deleteBook(@ShellOption(value = {"-id"}) long id) {
        bookService.deleteBook(id);
    }

    @ShellMethod(value = "book --find --all --by_author -author 'Bruce Eckel'", key = {"book --find --all --by_author"})
    public List<Book> findAllAuthorBooks(@ShellOption(value = {"-author"}) String authorName) {
        final Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new AuthorNotFoundException("Author with name '" + authorName + "' not exist"));
        return author.getBooks();
    }

    /**
     * Creates author
     */
    @ShellMethod(value = "author --create -name 'author name'",
            key = {"author --create"})
    public void createAuthor(@ShellOption(value = {"-name"}) String authorName) {
        authorService.createAuthor(authorName);
    }

    /**
     * Finds all authors
     */
    @ShellMethod(value = "author --find --all", key = {"author --find --all"})
    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }

    /**
     * Creates genre
     */
    @ShellMethod(value = "genre --create -name 'genre name'",
            key = {"genre --create"})
    public void createGenre(@ShellOption(value = {"-name"}) String genreName) {
        genreService.createGenre(genreName);
    }

    /**
     * Finds all genres
     */
    @ShellMethod(value = "genre --find --all", key = {"genre --find --all"})
    public List<Genre> findAllGenres() {
        return genreService.findAll();
    }

    /**
     * Creates comment
     */
    @ShellMethod(value = "comment --create -text 'comment text' -book_id 1",
            key = {"comment --create"})
    public void createComment(@ShellOption(value = {"-text"}) String commentText, @ShellOption(value = {"-book_id"}) long bookId) {
        commentService.createComment(commentText, bookId);
    }

    /**
     * Finds all comments
     */
    @ShellMethod(value = "comment --find --all", key = {"comment --find --all"})
    public List<Comment> findAllComments() {
        return commentService.findAll();
    }

}
