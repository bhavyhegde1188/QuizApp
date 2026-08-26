package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Returned immediately after POST /api/quiz/submit */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmitResponse {
    private Long attemptId;
    private int totalQuestions;
    private int attempted;
    private int correct;
    private int wrong;
    private double score;
}
