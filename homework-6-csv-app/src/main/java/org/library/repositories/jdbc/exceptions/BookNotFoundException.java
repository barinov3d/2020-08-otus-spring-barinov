package org.library.repositories.jdbc.exceptions;

public class BookNotFoundException extends RepositoryException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
