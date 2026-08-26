package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QuestionRequest {

    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotBlank
    private String optionA;

    @NotBlank
    private String optionB;

    @NotBlank
    private String optionC;

    @NotBlank
    private String optionD;

    @NotBlank
    @Pattern(regexp = "[ABCDabcd]", message = "correctAnswer must be one of A, B, C, D")
    private String correctAnswer;
}
