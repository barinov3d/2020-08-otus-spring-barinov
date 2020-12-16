package org.library.services;

import org.library.exceptions.GenreNotFoundException;
import org.library.models.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre save(Genre comment);

    Genre findById(String id) throws GenreNotFoundException;

    void deleteById(String id) throws GenreNotFoundException;
}
