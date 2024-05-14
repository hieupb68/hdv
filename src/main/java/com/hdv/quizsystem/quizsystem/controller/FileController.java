package com.hdv.quizsystem.quizsystem.controller;

import com.hdv.quizsystem.quizsystem.model.question.Question;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @PostMapping("/uploadStudentList")
    public void uploadStudentList(@RequestParam("file") MultipartFile file) {
        try {
            List<String> studentList = readStudentList(file);
            System.out.println("Student list uploaded successfully:");
            for (String student : studentList) {
                System.out.println(student);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error uploading student list", e);
        }
    }

    @PostMapping("/uploadQuestionFile")
    public void uploadQuestionFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Question> questions = readQuestionFile(file);
            System.out.println("Questions uploaded successfully:");
            for (Question q : questions) {
                System.out.println(q);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error uploading question file", e);
        }
    }

    private List<String> readStudentList(MultipartFile file) throws IOException {
        List<String> studentList = new ArrayList<>();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            for (XWPFParagraph para : document.getParagraphs()) {
                String content = para.getText();
                if (content.trim().startsWith("B20DC")) {
                    studentList.add(content.trim());
                }
            }
        }
        return studentList;
    }

    private List<Question> readQuestionFile(MultipartFile file) throws IOException {
        List<Question> questions = new ArrayList<>();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            Question currentQuestion = null;
            for (XWPFParagraph para : document.getParagraphs()) {
                String content = para.getText();
                if (content.startsWith("Câu")) {
                    if (currentQuestion != null) {
                        questions.add(currentQuestion);
                    }
                    currentQuestion = new Question();
                    String[] parts = content.split("\\.");
                    currentQuestion.setQuestion(parts[1].trim());
                } else if (content.startsWith("Đáp án đúng:")) {
                    if (currentQuestion != null) {
                        currentQuestion.setCorrectAnswer(content.split(":")[1].trim());
                    }
                } else if (content.matches("[A-D]\\. .*")) {
                    if (currentQuestion != null) {
                        currentQuestion.getOptions().add(content.substring(3).trim());
                    }
                }
            }
            if (currentQuestion != null) {
                questions.add(currentQuestion);
            }
        }
        return questions;
    }
}
