package com.hdv.quizsystem.quizsystem.repository;

import com.hdv.quizsystem.quizsystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsByUrlKey(String urlKey);
    Optional<Quiz> findByUrlKey(String urlKey);
}
