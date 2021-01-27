package org.library.repositories;

import org.library.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    @RestResource(path = "email", rel = "email")
    User findByEmail(String email);
}
