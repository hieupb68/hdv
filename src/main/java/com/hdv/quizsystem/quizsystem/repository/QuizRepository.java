package com.hdv.quizsystem.quizsystem.repository;

import com.hdv.quizsystem.quizsystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsByUrlKey(String urlKey);
}
