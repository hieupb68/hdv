package com.hdv.quizsystem.quizsystem.model.student;

public class Student {
    private String studentId;

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // Thêm constructor và getter/setter
    public String getStudent() {
        return studentId;
    }

    public void setStudent(String student) {
        this.studentId = student;
    }
}
