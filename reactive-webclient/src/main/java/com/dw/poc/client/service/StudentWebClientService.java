package com.dw.poc.client.service;

import com.dw.poc.client.dto.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class StudentWebClientService {

    private final WebClient webClient;

    public StudentWebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api").build();
    }

    public Flux<Student> findStudents() {
        return this.webClient.get().uri("/students")
                .retrieve()
                .bodyToFlux(Student.class);
    }

    public Mono<Student> findStudentById(Long id) {
        return this.webClient.get().uri("/student/{id}", id)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Student> saveStudent(Student student) {
        return this.webClient.post().uri("/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(student), Student.class)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(Student.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.error(new RuntimeException("Client Error: can't fetch student"));
                    } else if (clientResponse.statusCode().is5xxServerError()) {
                        return Mono.error(new RuntimeException("Server Error: can't fetch student"));
                    } else {
                        return clientResponse.createError();
                    }
                });
    }

    public Mono<Student> updateStudent(Student student) {
        return this.webClient.put().uri("/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Void> deleteStudent(Long id) {
        return this.webClient.delete().uri("/student/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
