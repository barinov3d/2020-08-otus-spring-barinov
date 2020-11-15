package org.library.repositories.jdbc;

import lombok.AllArgsConstructor;
import org.library.models.Author;
import org.library.models.Book;
import org.library.repositories.BookRepository;
import org.library.repositories.jdbc.exceptions.BookNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class BookRepositoryJdbc implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("select count(e) from Book e", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(long id) {
        final Optional<Book> optionalBook = Optional.ofNullable(em.find(Book.class, id));
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException("Book with id=" + "'" + id + " not found");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        final TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll(Author author) {
        return author.getBooks();
    }

    @Override
    @Transactional
    public void updateTitleById(long id, String title) {
        findById(id).setTitle(title);
    }

    @Override
    @Transactional
    public void updateCommentById(long id, String comment) {
        findById(id).setComment(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(em.merge(book));
    }

}
