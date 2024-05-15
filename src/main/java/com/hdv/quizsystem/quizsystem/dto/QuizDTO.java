package com.hdv.quizsystem.quizsystem.dto;

import com.hdv.quizsystem.quizsystem.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizDTO {
    private Long id;
    private String urlKey;
    private List<QuestionDTO> questions;
    private List<StudentDTO> students;

    public QuizDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.urlKey = quiz.getUrlKey();
        this.questions = quiz.getQuestions().stream().map(QuestionDTO::new).toList();
        this.students = quiz.getStudents().stream().map(StudentDTO::new).toList();
    }
}
