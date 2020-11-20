package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthor(Author author);

    Optional<Book> findBookByAuthorAndTitle(Author author, String title);

    @EntityGraph(value = "book-author-genre-entity-graph")
    List<Book> findAll();

    @EntityGraph(value = "book-author-genre-entity-graph")
    Optional<Book> findById(Long id);


}
