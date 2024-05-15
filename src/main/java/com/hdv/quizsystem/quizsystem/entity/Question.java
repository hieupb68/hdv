package com.hdv.quizsystem.quizsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = Answer_.QUESTION)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    private Quiz quiz;
}
