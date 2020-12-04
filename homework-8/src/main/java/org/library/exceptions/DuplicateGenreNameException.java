package org.library.exceptions;

public class DuplicateGenreNameException extends RepositoryException {
    public DuplicateGenreNameException(String message) {
        super(message);
    }
}
