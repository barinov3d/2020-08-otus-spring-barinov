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
@ToString(exclude = {"comments"})
@EqualsAndHashCode(of = {"title", "genre"})
@Document(collection = "books")
@NoArgsConstructor
public class Book {

    @Id
    private String id;
    @Field(name = "title")
    private String title;
    @Field(name = "comments")
    private List<Comment> comments = new ArrayList<>();
    @Field(name = "genre")
    private Genre genre;
    @Field(name = "author")
    @DBRef
    private Author author;

    public Book(String title, Genre genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }
    public void addComments(List<Comment> commentsToAdd) {
        comments.addAll(commentsToAdd);
    }

}
