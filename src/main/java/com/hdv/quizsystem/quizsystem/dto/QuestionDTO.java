package com.hdv.quizsystem.quizsystem.dto;

import com.hdv.quizsystem.quizsystem.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionDTO {
    private String question;
    private List<AnswerDTO> answers;

    public QuestionDTO(Question question) {
        this.question = question.getQuestion();
        this.answers = question.getAnswers().stream().map(AnswerDTO::new).toList();
    }
}
