package org.library.dao;

import org.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    void add(Genre genre);

    List<Genre> findAll();

    Optional<Genre> findByName(String name);

}
