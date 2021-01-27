package org.library.repositories;

import org.library.models.Author;
import org.library.repositories.impl.AuthorCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends MongoRepository<Author, String>, AuthorCustomizeRepository<Author, String> {

}
