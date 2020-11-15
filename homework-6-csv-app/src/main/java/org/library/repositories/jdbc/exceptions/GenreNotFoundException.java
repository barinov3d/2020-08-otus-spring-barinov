package org.library.repositories.jdbc.exceptions;

public class GenreNotFoundException extends RepositoryException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}
