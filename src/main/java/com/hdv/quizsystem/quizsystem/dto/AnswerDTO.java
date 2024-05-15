package com.hdv.quizsystem.quizsystem.dto;

import com.hdv.quizsystem.quizsystem.entity.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    private String key;
    private String content;
    private boolean correct;

    public AnswerDTO(Answer answer) {
        this.key = answer.getKey();
        this.content = answer.getAnswer();
        this.correct = answer.isCorrect();
    }
}
