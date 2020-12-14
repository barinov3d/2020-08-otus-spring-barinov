package org.library.repositories.impl;

import org.library.exceptions.DuplicateAuthorNameException;
import org.library.models.Author;
import org.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class AuthorCustomizeRepositoryImpl<T, ID> implements AuthorCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Author> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final List<Author> authors = mongoTemplate.find(query, Author.class);
        if (authors.size() == 1) {
            return Optional.ofNullable(authors.get(0));
        }
        if (authors.size() > 1) {
            throw new DuplicateAuthorNameException("Found " + authors.size() + " not unique names: " + name);
        }
        return Optional.empty();
    }

    @Override
    public <S extends T> S save(S entity) {
        Author author = (Author) entity;
        if (author.getId() == null) {
            findByName(author.getName())
                    .ifPresent(g -> {
                        throw new DuplicateAuthorNameException("Author with name '" + author.getName() + "' is already define in the scope");
                    });
        }
        mongoTemplate.save(author);
        return (S) author;
    }

    @Override
    public Author findAuthorByBook(Book book) {
        Query query = new Query();
        query.addCriteria(Criteria.where("books").in(book));
        return mongoTemplate.findOne(query, Author.class);
    }
}
