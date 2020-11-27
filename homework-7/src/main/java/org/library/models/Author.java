package org.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = "books")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
@NamedEntityGraph(name = "Author.books", attributeNodes = @NamedAttributeNode("books"))
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(targetEntity = Book.class, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> books;

}
