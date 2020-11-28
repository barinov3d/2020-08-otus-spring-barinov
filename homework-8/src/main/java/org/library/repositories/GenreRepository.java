package org.library.repositories;

import org.library.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

}
