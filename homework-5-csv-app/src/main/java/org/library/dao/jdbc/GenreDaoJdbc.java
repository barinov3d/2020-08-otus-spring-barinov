package org.library.dao.jdbc;

import org.library.dao.GenreDao;
import org.library.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public void add(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", genre.getName());
        jdbc.update("insert into genres (`name`) values (:name)", params);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query(
                "select id,name from genres", new GenreMapper());
    }

    @Override
    public Optional<Genre> findByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject("select id, name from genres where name = :name", params, new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
