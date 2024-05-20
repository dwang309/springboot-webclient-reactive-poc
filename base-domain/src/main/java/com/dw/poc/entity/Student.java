package com.dw.poc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Student {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
}