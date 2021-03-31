package org.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.webflux.model.Student;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
}
