package org.library.exceptions;

public class DuplicateAuthorNameException extends RepositoryException {
    public DuplicateAuthorNameException(String message) {
        super(message);
    }
}
