package com.hdv.quizsystem.quizsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlKey;

    @OneToMany(mappedBy = Question_.QUIZ, cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Student> students;
}
