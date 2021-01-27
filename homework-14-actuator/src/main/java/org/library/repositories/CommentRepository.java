package org.library.repositories;

import org.library.models.Comment;
import org.library.repositories.impl.CommentCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "comments")
public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomizeRepository<Comment, String> {

}
