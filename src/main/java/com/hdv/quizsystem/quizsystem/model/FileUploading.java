package com.hdv.quizsystem.quizsystem.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FileUploading {
    private List<MultipartFile> files;
}
