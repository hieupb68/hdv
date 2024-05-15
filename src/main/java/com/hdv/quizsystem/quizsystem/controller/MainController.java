package com.hdv.quizsystem.quizsystem.controller;
import com.hdv.quizsystem.quizsystem.entity.Question;
import com.hdv.quizsystem.quizsystem.entity.Quiz;
import com.hdv.quizsystem.quizsystem.model.FileUploading;
import com.hdv.quizsystem.quizsystem.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final QuizRepository quizRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/teacher.html")
    public String teacher() {
        return "teacher.html";
    }

    @GetMapping("/student.html")
    public String student() {
        return "student.html";
    }

    @GetMapping("/enterClass.html")
    public String enterClass() {
        return "enterClass.html";
    }

    @PostMapping("/join-test")
    public String joinTest(@RequestParam String testKey, @RequestParam String studentCode, Model model) {
        Optional<Quiz> found = quizRepository.findByUrlKey(testKey);

        if (found.isPresent()) {
            Quiz quiz = found.get();
            if (quiz.getStudents().stream().anyMatch(student -> student.getCode().equals(studentCode))) {
                model.addAttribute("quiz", quiz);
                return "quiz";
            } else {
                model.addAttribute("error", "You are not allowed to join this quiz");
            }
        } else {
            model.addAttribute("error", "Quiz not found");
        }

        return "enterClass";
    }
}
