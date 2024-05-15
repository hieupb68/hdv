package com.hdv.quizsystem.quizsystem.dto;

import com.hdv.quizsystem.quizsystem.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private String code;

    public StudentDTO(Student student) {
        this.code = student.getCode();
    }
}
