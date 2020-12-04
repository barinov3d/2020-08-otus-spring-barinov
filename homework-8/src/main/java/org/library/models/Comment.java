package org.library.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@ToString
@Document(collection = "comments")
@NoArgsConstructor
public class Comment {

    @Id
    private String id;
    @Field(name = "text")
    private String text;
    @Field(name = "date")
    private LocalDate date;

    public Comment(String text, LocalDate date) {
        this.text = text;
        this.date = date;
    }

}
