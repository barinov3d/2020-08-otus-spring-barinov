package org.library.repositories.impl;

import org.library.models.Genre;

import java.util.Optional;

public interface GenreCustomizeRepository<T, ID> {

    Optional<Genre> findByName(String name);

    <S extends T> S save(S entity);

}
