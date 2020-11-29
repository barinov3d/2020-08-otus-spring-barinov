package org.library.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private List<Comment> comments;
    @Field(name = "author")
    private Author author;

    @Field(name = "genre")
    private Genre genre;

    public Book(String title, List<Comment> comments, Author author, Genre genre) {
        this.title = title;
        this.comments = comments;
        this.author = author;
        this.genre = genre;
    }

}
