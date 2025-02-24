package com.quiz.dto;

import com.quiz.model.Option;
import com.quiz.model.Question;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuestionDTO {
    private Long id;
    private String text;
    private Integer points;
    private Integer orderIndex;
    private List<OptionDTO> options;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.points = question.getPoints();
        this.orderIndex = question.getOrderIndex();
        this.options = question.getOptions() == null ? new ArrayList<>() :
                question.getOptions().stream()
                        .map(OptionDTO::new)
                        .collect(Collectors.toList());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    // Option DTO inner class
    public static class OptionDTO {
        private Long id;
        private String text;
        private Integer orderIndex;

        public OptionDTO(Option option) {
            this.id = option.getId();
            this.text = option.getText();
            this.orderIndex = option.getOrderIndex();
        }

        // Getters
        public Long getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public Integer getOrderIndex() {
            return orderIndex;
        }
    }
} 