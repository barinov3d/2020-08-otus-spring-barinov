package org.library.dao.jdbc;

import org.library.dao.AuthorDao;
import org.library.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public void add(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        jdbc.update("insert into authors (`name`) values (:name)", params);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query(
                "select id,name from authors", new AuthorMapper());
    }

    @Override
    public Optional<Author> findByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject("select id, name from authors where name = :name", params, new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
