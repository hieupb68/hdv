package com.hdv.quizsystem.quizsystem.controller;

import com.hdv.quizsystem.quizsystem.entity.Answer;
import com.hdv.quizsystem.quizsystem.entity.Question;
import com.hdv.quizsystem.quizsystem.model.FileUploading;
import com.hdv.quizsystem.quizsystem.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private static final Pattern ANSWER_REGEX = Pattern.compile("([A-Z])\\.\\s*(.+)");

    private final QuestionRepository questionRepository;

    @PostMapping("/upload")
    public void uploadFiles(@ModelAttribute FileUploading fileUploading) {
        try {
//            List<String> students = readStudentList(fileUploading.getStudents());
            List<Question> questions = readQuestionFile(fileUploading.getQuestions());
            questionRepository.saveAll(questions);
        } catch (IOException e) {
            throw new RuntimeException("Bad malformed files. Ensure you have correct format for each file", e);
        }
    }

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
                    if (currentQuestion == null) throw new IOException();

                    String correctAnswerKey = content.split(":")[1].trim();
                    Answer correctAnswer = currentQuestion.getAnswers()
                        .stream()
                        .filter(ans -> correctAnswerKey.equals(ans.getKey()))
                        .findFirst()
                        .orElseThrow(IOException::new);
                    correctAnswer.setCorrect(true);
                } else {
                    if (currentQuestion == null) throw new IOException();

                    Matcher matcher = ANSWER_REGEX.matcher(content);
                    if (matcher.matches()) {
                        String key = matcher.group(1);
                        String ans = matcher.group(2);
                        currentQuestion.getAnswers().add(new Answer(key, ans));
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
