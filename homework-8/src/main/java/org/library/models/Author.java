package org.library.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "authors")
@EqualsAndHashCode(exclude = {"books"})
@ToString(exclude = "books")
@NoArgsConstructor
public class Author {

    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "books")
    @DBRef
    private List<Book> books;

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addBooks(List<Book> booksToAdd) {
        books.addAll(booksToAdd);
    }

}
