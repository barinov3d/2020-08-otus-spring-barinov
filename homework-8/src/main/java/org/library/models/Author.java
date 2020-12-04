package org.library.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "authors")
@EqualsAndHashCode(exclude = {"books"})
@ToString(exclude = "books")
public class Author {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "books")
    private List<Book> books = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }
}
