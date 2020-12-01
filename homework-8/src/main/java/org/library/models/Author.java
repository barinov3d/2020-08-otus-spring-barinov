package org.library.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "books")
@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    @Field(name = "name")
    @Indexed(unique = true)
    private String name;

    @Field(name = "books")
    @DBRef()
    private List<Book> books = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }
}
