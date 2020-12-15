package org.library.repositories.impl;

import org.library.exceptions.AuthorNotFoundException;
import org.library.exceptions.BookNotFoundException;
import org.library.exceptions.DuplicateAuthorBookException;
import org.library.models.Author;
import org.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookCustomizeRepositoryImpl<T, ID> implements BookCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Book> findAllByAuthor(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id_").is(author.getId()));
        final Author authorFromRepo = Optional.ofNullable(mongoTemplate.findOne(query, Author.class)).orElseThrow(() -> new AuthorNotFoundException("Author with name " + author.getName() + " not found"));
        List<Book> result;
        if (authorFromRepo.getBooks().size() == 0) {
            result = Collections.emptyList();
        } else {
            result = authorFromRepo.getBooks();
        }
        return result;
    }

    @Override
    public Optional<Book> findBookByAuthorAndTitle(Author author, String title) {
        final List<Book> books = findAllByAuthor(author);
        return Optional.of(books.stream().filter(b -> b.getTitle().equals(title)).findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with title " + title + " not found for " + author.getName())));
    }

    @Override
    public <S extends T> S save(S entity) {
        Book book = (Book) entity;
        if (book.getId() == null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(book.getTitle()));
            query.addCriteria(Criteria.where("genre").is(book.getGenre()));
            query.addCriteria(Criteria.where("author").is(book.getAuthor()));
            final Optional<Book> findedBook = Optional.ofNullable(mongoTemplate.findOne(query, Book.class));
            findedBook.ifPresent(g -> {
                throw new DuplicateAuthorBookException(
                        "Book with title '" + book.getTitle() + " and genre '" + book.getGenre() + "' is already define in the scope");
            });
        }
        mongoTemplate.save(book);
        return (S) book;
    }

}
