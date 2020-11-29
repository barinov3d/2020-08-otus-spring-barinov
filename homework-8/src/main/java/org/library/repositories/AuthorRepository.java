package org.library.repositories;

import org.library.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByName(String name);

    Optional<Author> findById(String id);

    void deleteById(String id);

    List<Author> findAll();

}
