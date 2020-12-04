package org.library.repositories;

import org.library.models.Author;
import org.library.repositories.impl.AuthorCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String>, AuthorCustomizeRepository<Author, String> {

}
