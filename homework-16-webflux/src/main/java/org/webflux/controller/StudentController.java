package org.webflux.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.webflux.model.Student;
import org.webflux.repositories.StudentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;

    @GetMapping("/students")
    public Flux<Student> readAll() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Mono<Student> readStudent(@PathVariable("id") String id) {
        return studentRepository.findById(id);
    }

    @PostMapping("/students")
    public Mono<Student> createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public Mono<Student> update(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("/students/{id}")
    public Mono<Void> deleteStudent(@PathVariable("id") String id) {
        return studentRepository.deleteById(id);
    }

}
