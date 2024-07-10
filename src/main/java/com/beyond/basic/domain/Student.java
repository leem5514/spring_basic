package com.beyond.basic.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student {
    private String name;
    private String email;
    private List<Grade> grades;

    public Student(String name, String email, List<Integer> grades) {
        this.name = name;
        this.email = email;
    }
}
@Data
class Grade {
    private String classNane;
    private String grade;
}