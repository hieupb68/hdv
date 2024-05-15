package com.hdv.quizsystem.quizsystem.repository;

import com.hdv.quizsystem.quizsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByCodeIn(List<String> codes);
}
