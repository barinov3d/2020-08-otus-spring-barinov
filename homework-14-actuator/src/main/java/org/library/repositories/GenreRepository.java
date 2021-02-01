package org.library.repositories;

import org.library.models.Genre;
import org.library.repositories.impl.GenreCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "genres")
public interface GenreRepository extends MongoRepository<Genre, String>, GenreCustomizeRepository<Genre, String> {

}
