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
@Document(collection = "student")
@ToString(exclude = "courses")
@Data
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @DBRef
    private List<Course> courses;

    public Student(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}


