package com.hdv.quizsystem.quizsystem.repository;

import com.hdv.quizsystem.quizsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
