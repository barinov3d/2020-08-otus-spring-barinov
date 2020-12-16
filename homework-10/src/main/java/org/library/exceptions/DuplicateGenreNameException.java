package org.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateGenreNameException extends RepositoryException {
    public DuplicateGenreNameException(String message) {
        super(message);
    }
}
