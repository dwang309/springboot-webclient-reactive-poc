package com.dw.poc.client.controller;

import com.dw.poc.client.dto.Student;
import com.dw.poc.client.service.StudentWebClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/web")
public class StudentController {

    private final StudentWebClientService webClientService;

    public StudentController(StudentWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Student> getStudentById(@PathVariable Long id){
        return webClientService.findStudentById(id);
    }

    @GetMapping(value = "/students", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<Student> findAllStudents() {
        return webClientService.findStudents();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Student> saveStudent(@RequestBody Student student) {
        return webClientService.saveStudent(student);
    }


    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Student> updateStudent(@RequestBody Student student) {
        return webClientService.updateStudent(student);
    }

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteUStudent(@PathVariable Long id) {
        return webClientService.deleteStudent(id);
    }
}
