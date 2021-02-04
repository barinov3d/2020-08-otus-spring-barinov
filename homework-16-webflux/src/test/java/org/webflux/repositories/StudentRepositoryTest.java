package org.webflux.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.webflux.Constants;
import org.webflux.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("org.webflux.repositories")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldFindAllStudents() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldReturnAllStudents() {
        Flux<Student> courseFlux = repository.findAll();

        StepVerifier
                .create(courseFlux)
                .recordWith(ArrayList::new)
                .expectNextCount(3)
                .consumeRecordedWith(results -> {
                    assertThat(results)
                            .extracting(Student::getFirstName)
                            .contains(Constants.FIRST_NAME_1, Constants.FIRST_NAME_2, Constants.FIRST_NAME_3);
                })
                .verifyComplete();
    }

    @Test
    @DirtiesContext
    void shouldSetId() {
        Mono<Student> courseMono = repository.save(new Student("Ivan", "Ivanov", LocalDate.of(2021, 1, 1)));

        StepVerifier
                .create(courseMono)
                .assertNext(course -> assertNotNull(course.getId()))
                .expectComplete()
                .verify();
    }


    @Test
    void shouldFindCorrectStudentById() {
        Mono<Student> monoStudent = repository.findById("1");

        StepVerifier.create(monoStudent)
                .assertNext((course -> assertEquals(course.getFirstName(), Constants.FIRST_NAME_1)))
                .expectComplete()
                .verify();

    }
}