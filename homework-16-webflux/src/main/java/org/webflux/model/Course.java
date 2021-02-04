package org.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "course")
@ToString(exclude = "students")
@Data
public class Course {
    @Id
    private String id;
    private String name;
    private LocalDate date;

    @DBRef
    private List<Student> students;

    public Course(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
