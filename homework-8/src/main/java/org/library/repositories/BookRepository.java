package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findAllByAuthor(Author author);

    Optional<Book> findBookByAuthorAndTitle(Author author, String title);

    List<Book> findAll();

    Optional<Book> findById(Long id);


}
