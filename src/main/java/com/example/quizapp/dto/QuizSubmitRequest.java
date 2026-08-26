package com.example.quizapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequest {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotNull(message = "topicId is required")
    private Long topicId;

    @NotEmpty(message = "At least one answer must be submitted")
    @Valid
    private List<AnswerSubmission> answers;

    @Data
    public static class AnswerSubmission {

        @NotNull(message = "questionId is required")
        private Long questionId;

        /** May be null/blank if the user skipped the question. */
        private String selectedAnswer;
    }
}
