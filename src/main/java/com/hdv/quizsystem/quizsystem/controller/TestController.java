package com.hdv.quizsystem.quizsystem.controller;

import com.hdv.quizsystem.quizsystem.dto.QuizDTO;
import com.hdv.quizsystem.quizsystem.entity.Answer;
import com.hdv.quizsystem.quizsystem.entity.Question;
import com.hdv.quizsystem.quizsystem.entity.Quiz;
import com.hdv.quizsystem.quizsystem.entity.Student;
import com.hdv.quizsystem.quizsystem.model.FileUploading;
import com.hdv.quizsystem.quizsystem.repository.QuestionRepository;
import com.hdv.quizsystem.quizsystem.repository.QuizRepository;
import com.hdv.quizsystem.quizsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {
    private static final Pattern ANSWER_REGEX = Pattern.compile("([A-Z])\\.\\s*(.+)");

    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;

    @PostMapping("/generate")
    public String generateTest(@ModelAttribute FileUploading fileUploading) {
        try {
            List<String> studentCodes = readStudentList(fileUploading.getStudents());
            String urlKey = generateRandomString();
            List<Question> questions = readQuestionFile(fileUploading.getQuestions());

            while (quizRepository.existsByUrlKey(urlKey)) {
                urlKey = generateRandomString();
            }

            List<Student> persistedStudents = studentRepository.findAllByCodeIn(studentCodes);
            studentCodes.removeAll(persistedStudents.stream().map(Student::getCode).toList());
            List<Student> toPersist = studentCodes.stream().map(Student::new).toList();
            studentRepository.saveAll(toPersist);
            persistedStudents.addAll(toPersist);

            Quiz quiz = new Quiz();
            quiz.setQuestions(questions);
            quiz.setStudents(persistedStudents);
            quiz.setUrlKey(urlKey);
            questions.forEach(question -> question.setQuiz(quiz));
            quizRepository.save(quiz);

            return urlKey;
        } catch (IOException e) {
            throw new RuntimeException("Bad malformed files. Ensure you have correct format for each file", e);
        }
    }

    @GetMapping("/{urlKey}")
    public QuizDTO getQuiz(@PathVariable String urlKey) {
        Quiz found = quizRepository.findByUrlKey(urlKey).orElseThrow();
        return new QuizDTO(found);
    }

    private String generateRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
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
                        currentQuestion.getAnswers().add(new Answer(key, ans, currentQuestion));
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
