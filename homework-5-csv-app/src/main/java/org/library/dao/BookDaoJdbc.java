package org.library.dao;

import org.library.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        jdbc.update("insert into books (id, `title`) values (:id, :title)", params);
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select * from books where id = :id", params, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Book> findAll() {
        return jdbc.query("select * from books", new BookMapper());
    }

    @Override
    public void updateTitle(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        jdbc.update("update books set title=:title where id=:id", params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("delete from books where id=:id", params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            final String title = resultSet.getString("title");
            final long author = resultSet.getLong("author_id");
            final long genre = resultSet.getLong("genre_id");
            return new Book(id, title, author, genre);
        }
    }
}
