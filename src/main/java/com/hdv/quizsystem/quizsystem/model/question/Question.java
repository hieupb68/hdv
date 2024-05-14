package com.hdv.quizsystem.quizsystem.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Question {
    private String question;
    private List<String> answers = new ArrayList<>();
    private String correctAnswer;
    private List<String> options = new ArrayList<>();

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public String getAnswer(int index) {
        if (index >= 0 && index < answers.size()) {
            return answers.get(index);
        } else {
            return null;
        }
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", options=" + options +
                '}';
    }
}
