package org.library.repositories.impl;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class AuthorCustomizeRepositoryImpl<T, ID> implements AuthorCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Author findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Author.class);
    }

    @Override
    public Author findAuthorByBook(Book book) {
        Query query = new Query();
        query.addCriteria(Criteria.where("books").in(book));
        return mongoTemplate.findOne(query, Author.class);
    }
}
