package com.quiz.controller;

import com.quiz.dto.QuestionDTO;
import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/competition")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/{accessCode}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCompetition(@PathVariable String accessCode) {
        List<Question> questions = questionRepository.findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode);
        
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(QuestionDTO::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(questionDTOs);
    }
} 