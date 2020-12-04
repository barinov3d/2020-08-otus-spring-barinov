package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;
import org.library.services.exceptions.DuplicateAuthorNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class BookCustomizeRepositoryImpl<T, ID> implements BookCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Book> findAllByAuthor(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").is(author));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public Optional<Book> findBookByAuthorAndTitle(Author author, String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").is(author));
        query.addCriteria(Criteria.where("title").is(title));
        return Optional.ofNullable(mongoTemplate.findOne(query, Book.class));
    }

}
