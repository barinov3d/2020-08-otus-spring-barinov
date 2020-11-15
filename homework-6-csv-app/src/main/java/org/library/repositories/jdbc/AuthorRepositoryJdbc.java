package org.library.repositories.jdbc;

import org.library.models.Author;
import org.library.repositories.AuthorRepository;
import org.library.repositories.jdbc.exceptions.AuthorNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Author findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new AuthorNotFoundException("Author with name '" + name + "' not found");
        }
    }

    @Override
    @Transactional
    public void updateNameById(long id, String name) {
        findById(id).setName(name);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Author author = em.find(Author.class, id);
        em.remove(em.merge(author));
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(long id) {
        final Optional<Author> optionalAuthor = Optional.ofNullable(em.find(Author.class, id));
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            throw new AuthorNotFoundException("Author with id=" + "'" + id + "' not found");
        }
    }
}
