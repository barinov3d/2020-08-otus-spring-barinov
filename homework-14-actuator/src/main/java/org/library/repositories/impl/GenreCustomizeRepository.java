package org.library.repositories.impl;

import org.library.models.Genre;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface GenreCustomizeRepository<T, ID> {

    @RestResource(path = "name", rel = "name")
    Optional<Genre> findByName(String name);

    <S extends T> S save(S entity);

}
