package org.library.repositories;

import org.library.models.Genre;

import java.util.List;

public interface GenreRepository {

    Genre save(Genre genre);

    List<Genre> findAll();

    Genre findByName(String name);

    void updateNameById(long id, String name);

    void deleteById(long id);

    Genre findById(long id);

}
