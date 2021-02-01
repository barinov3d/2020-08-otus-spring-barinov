package org.library.repositories;

import org.library.models.Book;
import org.library.repositories.impl.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books")
public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository<Book, String> {

}
