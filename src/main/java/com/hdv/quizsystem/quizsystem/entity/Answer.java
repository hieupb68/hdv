package com.hdv.quizsystem.quizsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String answer;

    private boolean correct;

    @ManyToOne
    private Question question;

    public Answer(String key, String answer, Question question) {
        this.key = key;
        this.answer = answer;
        this.question = question;
        this.correct = false;
    }
}
