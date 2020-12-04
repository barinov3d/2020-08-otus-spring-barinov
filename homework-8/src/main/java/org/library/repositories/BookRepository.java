package org.library.repositories;

import org.library.models.Book;
import org.library.repositories.impl.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository<Book, String> {

}
