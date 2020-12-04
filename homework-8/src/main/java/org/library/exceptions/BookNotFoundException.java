package org.library.exceptions;

public class BookNotFoundException extends RepositoryException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
