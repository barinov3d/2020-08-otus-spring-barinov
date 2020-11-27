package org.library.models;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude = {"comments"})
@EqualsAndHashCode(of = {"id", "title"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-author-genre-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
