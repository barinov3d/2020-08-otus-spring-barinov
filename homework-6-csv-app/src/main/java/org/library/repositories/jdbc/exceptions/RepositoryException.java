package org.library.repositories.jdbc.exceptions;

import java.util.Objects;

public class RepositoryException extends RuntimeException {
    protected RepositoryException(final String message) {
        super(Objects.requireNonNull(message), null, false, false);
    }
}
