package com.quiz.controller;

import com.quiz.model.Competition;
import com.quiz.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompetitionControllerTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private CompetitionController competitionController;

    private Competition competition1;
    private Competition competition2;

    @BeforeEach
    void setUp() {
        // Test verilerini hazırla
        competition1 = new Competition();
        competition1.setName("Test Competition 1");
        competition1.setDescription("Test Description 1");
        competition1.setStartDate(LocalDateTime.now());
        competition1.setEndDate(LocalDateTime.now().plusDays(7));
        competition1.setActive(true);
        competition1.setAccessCode("test-access-code-1");

        competition2 = new Competition();
        competition2.setName("Test Competition 2");
        competition2.setDescription("Test Description 2");
        competition2.setStartDate(LocalDateTime.now().plusDays(1));
        competition2.setEndDate(LocalDateTime.now().plusDays(14));
        competition2.setActive(true);
        competition2.setAccessCode("test-access-code-2");
    }

    @Test
    void whenGetAllCompetitions_thenReturnCompetitionsList() {
        // given
        when(competitionRepository.count()).thenReturn(2L);
        when(competitionRepository.findAll()).thenReturn(Arrays.asList(competition1, competition2));

        // when
        List<Competition> result = competitionController.getAllCompetitions();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(competitionRepository, never()).saveAll(any());
        verify(competitionRepository, times(1)).findAll();
    }

    @Test
    void whenGetAllCompetitionsWithEmptyDB_thenCreateAndReturnCompetitions() {
        // given
        when(competitionRepository.count()).thenReturn(0L);
        when(competitionRepository.findAll()).thenReturn(Arrays.asList(competition1, competition2));
        when(competitionRepository.saveAll(any())).thenReturn(Arrays.asList(competition1, competition2));

        // when
        List<Competition> result = competitionController.getAllCompetitions();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(competitionRepository, times(1)).saveAll(any());
        verify(competitionRepository, times(1)).findAll();
    }

    @Test
    void whenGetCompetitionByValidAccessCode_thenReturnCompetition() {
        // given
        String accessCode = "test-access-code-1";
        when(competitionRepository.findByAccessCode(accessCode)).thenReturn(Optional.of(competition1));

        // when
        ResponseEntity<Competition> response = competitionController.getCompetitionByAccessCode(accessCode);

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(competition1.getName(), response.getBody().getName());
        verify(competitionRepository, times(1)).findByAccessCode(accessCode);
    }

    @Test
    void whenGetCompetitionByInvalidAccessCode_thenReturn404() {
        // given
        String accessCode = "invalid-access-code";
        when(competitionRepository.findByAccessCode(accessCode)).thenReturn(Optional.empty());

        // when
        ResponseEntity<Competition> response = competitionController.getCompetitionByAccessCode(accessCode);

        // then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(competitionRepository, times(1)).findByAccessCode(accessCode);
    }
} 