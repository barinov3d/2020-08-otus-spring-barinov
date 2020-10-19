package org.library.dao.jdbc;

import lombok.AllArgsConstructor;
import org.library.dao.BookDao;
import org.library.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@AllArgsConstructor
@Transactional
@Repository
public class BookDaoJdbc implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(e) from Book e", Long.class).getSingleResult();
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
        final TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();

    }

    @Override
    public void updateTitleById(long id, String title) {
        findById(id).setTitle(title);
    }

    @Override
    public void updateCommentById(long id, String comment) {
        findById(id).setComment(comment);
    }

    @Override
    public void deleteById(long id) {
        em.remove(findById(id));
    }
}
