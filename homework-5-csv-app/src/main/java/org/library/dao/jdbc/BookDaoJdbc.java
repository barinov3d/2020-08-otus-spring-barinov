package org.library.dao.jdbc;

import lombok.AllArgsConstructor;
import org.library.dao.AuthorDao;
import org.library.dao.BookDao;
import org.library.dao.GenreDao;
import org.library.domain.Author;
import org.library.domain.Book;
import org.library.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
    }

    @Override
    public void add(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("author_id", book.getAuthor().getId());
        params.put("genre_id", book.getGenre().getId());
        jdbc.update("insert into books (`title`,`author_id`,`genre_id`) values (:title,:author_id,:genre_id)", params);
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject("SELECT b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name FROM Books " +
                    "b INNER JOIN Authors a ON b.author_id = a.id INNER JOIN Genres g ON  b.genre_id = g.id" +
                    " where b.id = :id", params, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Book> findAll() {
        return jdbc.query(
                "SELECT b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name FROM Books " +
                        "b INNER JOIN Authors a ON b.author_id = a.id INNER JOIN Genres g ON  b.genre_id = g.id", new BookMapper());
    }

    @Override
    public void update(Book book) {
        final long id = book.getId();
        final String authorName = book.getAuthor().getName();
        final String genreName = book.getGenre().getName();
        if(findById(id).isEmpty()) {
            throw new RuntimeException("Book with id '" + id + "' not exist");
        }
        final Book existingBook = findById(id).get();
        final Optional<Author> authorOptional = authorDao.findByName(authorName);
        if (authorOptional.isEmpty()) {
            authorDao.add(new Author(0, authorName));
        }
        final Optional<Genre> genreOptional = genreDao.findByName(genreName);
        if (genreOptional.isEmpty()) {
            genreDao.add(new Genre(0, genreName));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("title", book.getTitle());
        params.put("author_id", authorDao.findByName(authorName).get().getId());
        params.put("genre_id", genreDao.findByName(genreName).get().getId());
        jdbc.update("update books set `title`=:title,`author_id`=:author_id,`genre_id`=:genre_id where id=:id", params);
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
            final long authorId = resultSet.getLong("author_id");
            final String authorName = resultSet.getString("author_name");
            final long genreId = resultSet.getLong("genre_id");
            final String genreName = resultSet.getString("genre_name");
            return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
