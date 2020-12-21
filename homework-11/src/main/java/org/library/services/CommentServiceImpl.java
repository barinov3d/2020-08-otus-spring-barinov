package org.library.services;

import org.library.exceptions.CommentNotFoundException;
import org.library.models.Comment;
import org.library.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(String id) throws CommentNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(String.format("Comment with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) throws CommentNotFoundException {
        if (commentRepository.findById(id).isEmpty()) {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found", id));
        }
        commentRepository.deleteById(id);
    }
}
