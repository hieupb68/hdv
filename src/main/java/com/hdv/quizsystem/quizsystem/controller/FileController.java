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

}
