package org.library.exceptions;

public class DuplicateAuthorBookException extends RepositoryException {
    public DuplicateAuthorBookException(String message) {
        super(message);
    }
}
