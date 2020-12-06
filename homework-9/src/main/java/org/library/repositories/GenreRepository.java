package org.library.repositories;

import org.library.models.Genre;
import org.library.repositories.impl.GenreCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String>, GenreCustomizeRepository<Genre, String> {

}
