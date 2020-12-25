package org.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAuthorNameException extends RepositoryException {
    public DuplicateAuthorNameException(String message) {
        super(message);
    }
}
