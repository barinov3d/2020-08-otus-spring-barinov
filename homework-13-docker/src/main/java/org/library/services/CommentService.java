package org.library.services;

import org.library.exceptions.CommentNotFoundException;
import org.library.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment save(Comment comment) throws CommentNotFoundException;

    Comment findById(String id) throws CommentNotFoundException;

    void deleteById(String id) throws CommentNotFoundException;
}
