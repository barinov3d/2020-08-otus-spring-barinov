package org.library.dao.jdbc;

import lombok.AllArgsConstructor;
import org.library.dao.BookDao;
import org.library.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Transactional
@Repository
public class BookDaoJdbc implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery(
                "select count(e) from Book e", Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }


    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        final TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void updateTitleById(long id, String title) {
        Query query = em.createQuery("update Book b set b.title = :title where b.id = :id");
        query.setParameter("id", id);
        query.setParameter("title", title);
        query.executeUpdate();
    }

    @Override
    public void updateCommentById(long id, String comment) {
        final Book book = findById(id);
        book.setComment(comment);
        em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        em.remove(findById(id));
    }
}
