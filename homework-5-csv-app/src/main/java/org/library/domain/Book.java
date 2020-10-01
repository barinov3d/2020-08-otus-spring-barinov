package org.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final long id;
    private final String title;
    private final long authorId;
    private final long genreId;
}
