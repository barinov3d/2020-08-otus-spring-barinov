package org.library.repositories;

import org.library.models.Comment;
import org.library.repositories.impl.CommentCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomizeRepository<Comment, String> {

}
