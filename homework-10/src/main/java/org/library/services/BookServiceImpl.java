package org.library.services;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.BookNotFoundException;
import org.library.exceptions.DuplicateAuthorBookException;
import org.library.models.Author;
import org.library.models.Book;
import org.library.repositories.AuthorRepository;
import org.library.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    final BookRepository bookRepository;
    final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public Book findByTitle(String name) throws BookNotFoundException {
        final Book book = bookRepository.findByName(name);
        if (book == null) {
            throw new BookNotFoundException(String.format("Book with name: %s not found", name));
        }
        return book;
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Book> books) {
        bookRepository.deleteAll(books);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByAuthor(Author author) throws AuthorNotFoundException {
        final Author authorFromRepo = authorRepository.findByName(author.getName());
        if (authorFromRepo == null) {
            throw new AuthorNotFoundException("Author with name " + author.getName() + " not found");
        }
        return authorFromRepo.getBooks();
    }

    @Override
    public Book findBookByAuthorAndTitle(Author author, String title) throws BookNotFoundException {
        final List<Book> books = findAllByAuthor(author);
        return books.stream().filter(b -> b.getTitle().equals(title)).findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with title " + title + " not found for " + author.getName()));
    }

    @Override
    public Book save(Book book) throws DuplicateAuthorBookException {
        if (book.getId() == null && bookRepository.isAuthorBookAlreadyExist(book)) {
            throw new DuplicateAuthorBookException(
                    "Book with title '" + book.getTitle() + " and genre '" + book.getGenre() + "' is already define in the scope");
        }
        return bookRepository.save(book);
    }


}
