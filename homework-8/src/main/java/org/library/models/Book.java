package org.library.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"comments"})
@EqualsAndHashCode(of = {"id", "title"})
@Document(collection = "books")

public class Book {
    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "comments")
    @DBRef()
    private List<Comment> comments = new ArrayList<>();

    @Field(name = "author")
    @DBRef()
    private Author author;

    @Field(name = "genre")
    private Genre genre;

    public Book(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

}
