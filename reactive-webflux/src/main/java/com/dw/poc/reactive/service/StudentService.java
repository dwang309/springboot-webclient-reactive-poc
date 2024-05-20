package com.dw.poc.reactive.service;

import com.dw.poc.entity.Student;
import com.dw.poc.reactive.repository.StudentRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Mono<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Flux<Student> getStudents() {
//        return studentRepository.findAll().delayElements(Duration.ofSeconds(2));
        return studentRepository.findAll();
    }

    public Mono<Student> addStudent(Student student) {
        return studentRepository.save(student);
//        response.subscribe();
    }

    public Mono<Student> updateStudent(Student student) {
        return studentRepository.findById(student.getId())
                .switchIfEmpty(Mono.error(new Exception("Student Not Found!")))
                .map(oldStudent -> {
                    if (student.getLastName() != null) {
                        oldStudent.setLastName(student.getLastName());
                    }
                    if (student.getFirstName() != null) {
                        oldStudent.setFirstName(student.getFirstName());
                    }
                    if (student.getEmailAddress() != null) {
                        oldStudent.setEmailAddress(student.getEmailAddress());
                    }
                    return oldStudent;
                })
                .flatMap(studentRepository::save);
    }

    public Mono<Void> deleteStudent(Long id) {
        return studentRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new Exception("Student Not Found!")));
    }

    public void generate() {
        Faker faker = new Faker();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Student student = new Student(null,
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress());
            students.add(student);
        }
        studentRepository.saveAll(students).subscribe();
    }
}
