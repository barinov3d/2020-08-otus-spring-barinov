package org.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    @Field(name = "book")
    private Book book;
    @Field(name = "text")
    private String text;

    @Field(name = "date")
    private LocalDate date;

    public Comment(Book book, String text, LocalDate date) {
        this.book = book;
        this.text = text;
        this.date = date;
    }

}
