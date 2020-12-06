package org.library.exceptions;

public class GenreNotFoundException extends RepositoryException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}
