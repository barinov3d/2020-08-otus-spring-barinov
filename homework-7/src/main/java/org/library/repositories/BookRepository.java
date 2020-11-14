package org.library.repositories;

import org.library.models.Author;
import org.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthor(Author author);

    Book findBookByAuthorAndTitle(Author author, String title);

}
