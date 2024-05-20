package com.dw.poc.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
}