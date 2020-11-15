package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(value = "Author.books")
    Author findByName(String name);

}
