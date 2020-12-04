package org.library.repositories.impl;

import org.library.exceptions.DuplicateGenreNameException;
import org.library.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class GenreCustomizeRepositoryImpl<T, ID> implements GenreCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Genre> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        final List<Genre> genres = mongoTemplate.find(query, Genre.class);
        if (genres.size() == 1) {
            return Optional.ofNullable(genres.get(0));
        }
        if (genres.size() > 1) {
            throw new DuplicateGenreNameException("Found " + genres.size() + " not unique names: " + name);
        }
        return Optional.empty();
    }

    @Override
    public <S extends T> S save(S entity) {
        Genre genre = (Genre) entity;
        if (genre.getId() == null) {
            findByName(genre.getName())
                    .ifPresent(g -> {
                        throw new DuplicateGenreNameException("Genre with name '" + genre.getName() + "' is already define in the scope");
                    });
        }
        mongoTemplate.save(genre);
        return (S) genre;
    }
}
