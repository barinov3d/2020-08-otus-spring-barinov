package org.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.webflux.model.Course;

public interface CourseRepository extends ReactiveMongoRepository<Course, String> {
}
