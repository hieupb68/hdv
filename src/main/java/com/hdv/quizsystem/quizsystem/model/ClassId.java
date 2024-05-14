package com.hdv.quizsystem.quizsystem.model;

public class ClassId {
    private Long id;
    private String classId;

    public ClassId(Long id, String classId) {
        this.id = id;
        this.classId = classId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", classId='" + classId + '\'' +
                '}';
    }
}
