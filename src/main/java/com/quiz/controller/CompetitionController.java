package com.quiz.controller;

import com.quiz.model.Competition;
import com.quiz.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;

    @GetMapping
    public List<Competition> getAllCompetitions() {
        // Örnek veri oluştur
        if (competitionRepository.count() == 0) {
            Competition comp1 = new Competition();
            comp1.setName("Java Spring Boot Yarışması");
            comp1.setDescription("Spring Boot ile REST API geliştirme yarışması");
            comp1.setStartDate(LocalDateTime.now());
            comp1.setEndDate(LocalDateTime.now().plusDays(7));
            comp1.setActive(true);

            Competition comp2 = new Competition();
            comp2.setName("Frontend Geliştirme Yarışması");
            comp2.setDescription("React ve Angular ile web uygulama geliştirme yarışması");
            comp2.setStartDate(LocalDateTime.now().plusDays(1));
            comp2.setEndDate(LocalDateTime.now().plusDays(14));
            comp2.setActive(true);

            competitionRepository.saveAll(Arrays.asList(comp1, comp2));
        }

        return competitionRepository.findAll();
    }

    @GetMapping("/{accessCode}")
    public ResponseEntity<Competition> getCompetitionByAccessCode(@PathVariable String accessCode) {
        return competitionRepository.findByAccessCode(accessCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 