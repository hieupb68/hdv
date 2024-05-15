package com.hdv.quizsystem.quizsystem.controller;
import com.hdv.quizsystem.quizsystem.entity.Question;
import com.hdv.quizsystem.quizsystem.entity.Quiz;
import com.hdv.quizsystem.quizsystem.model.FileUploading;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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
}
