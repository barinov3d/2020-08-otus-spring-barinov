package org.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAuthorBookException extends RepositoryException {
    public DuplicateAuthorBookException(String message) {
        super(message);
    }
}
