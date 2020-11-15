package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthor(Author author);

    Book findBookByAuthorAndTitle(Author author, String title);

    @EntityGraph(value = "book-author-genre-entity-graph")
    List<Book> findAll();

}
