package com.quiz.controller;

import com.quiz.dto.QuestionDTO;
import com.quiz.model.Competition;
import com.quiz.model.Option;
import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionController questionController;

    private Question question1;
    private Question question2;
    private String accessCode;
    private Competition competition;

    @BeforeEach
    void setUp() {
        accessCode = "test-access-code";

        // Create competition
        competition = new Competition();
        competition.setId(1L);
        competition.setAccessCode(accessCode);

        // Create first question with options
        question1 = new Question();
        question1.setId(1L);
        question1.setText("Question 1");
        question1.setPoints(10);
        question1.setOrderIndex(1);
        question1.setCompetition(competition);

        Option option1 = new Option();
        option1.setId(1L);
        option1.setText("Option 1");
        option1.setOrderIndex(1);
        option1.setCorrect(true);
        option1.setQuestion(question1);

        question1.setOptions(Arrays.asList(option1));

        // Create second question with options
        question2 = new Question();
        question2.setId(2L);
        question2.setText("Question 2");
        question2.setPoints(15);
        question2.setOrderIndex(2);
        question2.setCompetition(competition);

        Option option2 = new Option();
        option2.setId(2L);
        option2.setText("Option 2");
        option2.setOrderIndex(1);
        option2.setCorrect(false);
        option2.setQuestion(question2);

        question2.setOptions(Arrays.asList(option2));
    }

    @Test
    void whenGetQuestionsByValidAccessCode_thenReturnQuestions() {
        // given
        List<Question> questions = Arrays.asList(question1, question2);
        when(questionRepository.findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode))
                .thenReturn(questions);

        // when
        ResponseEntity<List<QuestionDTO>> response = questionController.getQuestionsByCompetition(accessCode);

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        
        // Verify first question
        QuestionDTO firstQuestion = response.getBody().get(0);
        assertEquals(question1.getId(), firstQuestion.getId());
        assertEquals(question1.getText(), firstQuestion.getText());
        assertEquals(question1.getPoints(), firstQuestion.getPoints());
        assertEquals(question1.getOrderIndex(), firstQuestion.getOrderIndex());
        assertEquals(1, firstQuestion.getOptions().size());
        
        // Verify first question's option
        QuestionDTO.OptionDTO firstOption = firstQuestion.getOptions().get(0);
        assertEquals("Option 1", firstOption.getText());
        assertEquals(1, firstOption.getOrderIndex());
        
        verify(questionRepository, times(1))
                .findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode);
    }

    @Test
    void whenGetQuestionsByInvalidAccessCode_thenReturn404() {
        // given
        when(questionRepository.findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode))
                .thenReturn(new ArrayList<>());

        // when
        ResponseEntity<List<QuestionDTO>> response = questionController.getQuestionsByCompetition(accessCode);

        // then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(questionRepository, times(1))
                .findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode);
    }

    @Test
    void whenGetQuestionsByValidAccessCode_withNoOptions_thenReturnQuestionsWithEmptyOptions() {
        // given
        question1.setOptions(new ArrayList<>());
        question2.setOptions(new ArrayList<>());
        List<Question> questions = Arrays.asList(question1, question2);
        
        when(questionRepository.findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode))
                .thenReturn(questions);

        // when
        ResponseEntity<List<QuestionDTO>> response = questionController.getQuestionsByCompetition(accessCode);

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        
        // Verify questions have empty options
        assertTrue(response.getBody().get(0).getOptions().isEmpty());
        assertTrue(response.getBody().get(1).getOptions().isEmpty());
    }

    @Test
    void whenGetQuestionsByValidAccessCode_withNullFields_thenHandleGracefully() {
        // given
        Question questionWithNulls = new Question();
        questionWithNulls.setId(null);
        questionWithNulls.setText(null);
        questionWithNulls.setPoints(null);
        questionWithNulls.setOrderIndex(null);
        questionWithNulls.setCompetition(competition);
        questionWithNulls.setOptions(new ArrayList<>());

        when(questionRepository.findByCompetitionAccessCodeOrderByOrderIndexAsc(accessCode))
                .thenReturn(Arrays.asList(questionWithNulls));

        // when
        ResponseEntity<List<QuestionDTO>> response = questionController.getQuestionsByCompetition(accessCode);

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        
        QuestionDTO questionDTO = response.getBody().get(0);
        assertNull(questionDTO.getId());
        assertNull(questionDTO.getText());
        assertNull(questionDTO.getPoints());
        assertNull(questionDTO.getOrderIndex());
        assertTrue(questionDTO.getOptions().isEmpty());
    }
} 