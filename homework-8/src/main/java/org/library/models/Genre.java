package org.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "genres")
public class Genre {
    @Id
    private long id;
    @Field(name = "name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
