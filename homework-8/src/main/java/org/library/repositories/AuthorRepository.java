package org.library.repositories;

import org.library.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Optional<Author> findByName(String name);

    Optional<Author> findById(Long id);

    void deleteById(Long id);

    List<Author> findAll();

}
