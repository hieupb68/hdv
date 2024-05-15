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

    @OneToMany(mappedBy = Question_.QUIZ)
    private List<Question> questions;

    @ManyToMany
    private List<Student> students;
}
