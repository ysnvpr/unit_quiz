package com.quiz.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionTest {

    @Test
    void whenCreatingCompetition_thenAllFieldsAreSetCorrectly() {
        // given
        Competition competition = new Competition();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(7);

        // when
        competition.setName("Test Competition");
        competition.setDescription("Test Description");
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        competition.setActive(true);
        competition.setAccessCode("test-access-code");

        // then
        assertEquals("Test Competition", competition.getName());
        assertEquals("Test Description", competition.getDescription());
        assertEquals(startDate, competition.getStartDate());
        assertEquals(endDate, competition.getEndDate());
        assertTrue(competition.isActive());
        assertEquals("test-access-code", competition.getAccessCode());
    }

    @Test
    void whenCreatingCompetition_thenAccessCodeIsGeneratedAutomatically() {
        // given
        Competition competition = new Competition();

        // when
        competition.onCreate();

        // then
        assertNotNull(competition.getAccessCode());
        assertEquals(32, competition.getAccessCode().length()); // UUID without dashes
    }
} 