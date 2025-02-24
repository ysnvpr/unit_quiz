package com.quiz.dto;

import com.quiz.model.Option;
import com.quiz.model.Question;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDTOTest {

    @Test
    void whenCreateQuestionDTO_thenMapAllFields() {
        // given
        Question question = new Question();
        question.setId(1L);
        question.setText("Test Question");
        question.setPoints(10);
        question.setOrderIndex(1);

        Option option1 = new Option();
        option1.setId(1L);
        option1.setText("Option 1");
        option1.setOrderIndex(1);
        option1.setCorrect(true);

        Option option2 = new Option();
        option2.setId(2L);
        option2.setText("Option 2");
        option2.setOrderIndex(2);
        option2.setCorrect(false);

        question.setOptions(Arrays.asList(option1, option2));

        // when
        QuestionDTO questionDTO = new QuestionDTO(question);

        // then
        assertEquals(1L, questionDTO.getId());
        assertEquals("Test Question", questionDTO.getText());
        assertEquals(10, questionDTO.getPoints());
        assertEquals(1, questionDTO.getOrderIndex());
        
        // verify options
        assertEquals(2, questionDTO.getOptions().size());
        
        QuestionDTO.OptionDTO firstOption = questionDTO.getOptions().get(0);
        assertEquals(1L, firstOption.getId());
        assertEquals("Option 1", firstOption.getText());
        assertEquals(1, firstOption.getOrderIndex());
        // verify that isCorrect is not mapped to DTO
        assertFalse(Arrays.stream(firstOption.getClass().getDeclaredFields())
                .anyMatch(field -> field.getName().equals("isCorrect")));
    }

    @Test
    void whenCreateQuestionDTOWithNullOptions_thenCreateEmptyOptionsList() {
        // given
        Question question = new Question();
        question.setId(1L);
        question.setText("Test Question");
        question.setPoints(10);
        question.setOrderIndex(1);
        question.setOptions(null);

        // when
        QuestionDTO questionDTO = new QuestionDTO(question);

        // then
        assertNotNull(questionDTO.getOptions());
        assertTrue(questionDTO.getOptions().isEmpty());
    }

    @Test
    void whenCreateQuestionDTOWithEmptyOptions_thenCreateEmptyOptionsList() {
        // given
        Question question = new Question();
        question.setId(1L);
        question.setText("Test Question");
        question.setPoints(10);
        question.setOrderIndex(1);
        question.setOptions(new ArrayList<>());

        // when
        QuestionDTO questionDTO = new QuestionDTO(question);

        // then
        assertNotNull(questionDTO.getOptions());
        assertTrue(questionDTO.getOptions().isEmpty());
    }

    @Test
    void whenCreateOptionDTO_thenMapAllFields() {
        // given
        Option option = new Option();
        option.setId(1L);
        option.setText("Test Option");
        option.setOrderIndex(1);
        option.setCorrect(true);

        // when
        QuestionDTO.OptionDTO optionDTO = new QuestionDTO.OptionDTO(option);

        // then
        assertEquals(1L, optionDTO.getId());
        assertEquals("Test Option", optionDTO.getText());
        assertEquals(1, optionDTO.getOrderIndex());
        // verify that isCorrect is not exposed
        assertFalse(Arrays.stream(optionDTO.getClass().getDeclaredFields())
                .anyMatch(field -> field.getName().equals("isCorrect")));
    }

    @Test
    void whenCreateOptionDTOWithNullValues_thenHandleGracefully() {
        // given
        Option option = new Option();
        // id will be null
        option.setText(null);
        // orderIndex will be null

        // when
        QuestionDTO.OptionDTO optionDTO = new QuestionDTO.OptionDTO(option);

        // then
        assertNull(optionDTO.getId());
        assertNull(optionDTO.getText());
        assertNull(optionDTO.getOrderIndex());
    }
} 