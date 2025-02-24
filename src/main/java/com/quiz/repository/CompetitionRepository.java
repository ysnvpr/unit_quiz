package com.quiz.repository;

import com.quiz.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Competition> findByAccessCode(String accessCode);
} 