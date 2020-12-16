package org.library.repositories.impl;

import org.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

public class BookCustomizeRepositoryImpl<T, ID> implements BookCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean isAuthorBookAlreadyExist(Book book) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(book.getTitle()));
        query.addCriteria(Criteria.where("genre").is(book.getGenre()));
        query.addCriteria(Criteria.where("author").is(book.getAuthor()));
        final Optional<Book> findedBook = Optional.ofNullable(mongoTemplate.findOne(query, Book.class));
        return findedBook.isPresent();
    }

}
