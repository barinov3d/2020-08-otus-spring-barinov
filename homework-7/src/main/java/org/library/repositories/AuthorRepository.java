package org.library.repositories;

import org.library.models.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(value = "Author.books")
    Optional<Author> findByName(String name);

    @EntityGraph(value = "Author.books")
    Optional<Author> findById(Long id);

    @EntityGraph(value = "Author.books")
    void deleteById(Long id);

    @EntityGraph(value = "Author.books")
    List<Author> findAll();

}
