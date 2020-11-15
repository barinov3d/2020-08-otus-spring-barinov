package org.library.repositories.jdbc.exceptions;

public class AuthorNotFoundException extends RepositoryException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
