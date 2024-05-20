package com.dw.poc.reactive.api;

import com.dw.poc.entity.Student;
import com.dw.poc.reactive.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class StudentApi {

    private final StudentService studentService;

    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student/generate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void generateMockData() {
        studentService.generate();
    }

    @GetMapping(value = "/students", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<Student> getAllStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/student/{id}")
    public Mono<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Student> saveStudent(@RequestBody Student student) {
        Mono<Student> response = studentService.addStudent(student);
        return response;

    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Student> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
