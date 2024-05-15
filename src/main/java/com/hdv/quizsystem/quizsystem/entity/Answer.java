package com.hdv.quizsystem.quizsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Answer(String key, String answer) {
        this.key = key;
        this.answer = answer;
        this.correct = false;
    }
}
