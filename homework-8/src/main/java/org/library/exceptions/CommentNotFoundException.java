package org.library.exceptions;

public class CommentNotFoundException extends RepositoryException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
