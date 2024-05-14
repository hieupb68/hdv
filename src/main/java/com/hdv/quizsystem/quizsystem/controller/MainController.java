package com.hdv.quizsystem.quizsystem.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class MainController {
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

    @GetMapping("/generateClassId")
    public ResponseEntity<String> generateClassId() {
        // Generate a random class ID
        String classId = generateRandomString();
        // Return the class ID as a response
        return ResponseEntity.ok(classId);
    }

    // Method to generate a random string
    private String generateRandomString() {
        // Generate a random string for the ClassID
        String classId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return classId;
    }
}
