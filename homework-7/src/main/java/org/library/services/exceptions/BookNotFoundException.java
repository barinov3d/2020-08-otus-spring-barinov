package org.library.services.exceptions;

public class BookNotFoundException extends RepositoryException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
