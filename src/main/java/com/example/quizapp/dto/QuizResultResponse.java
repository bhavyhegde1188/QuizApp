package com.example.quizapp.dto;

import com.example.quizapp.entity.QuizAttempt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultResponse {

    private Long attemptId;
    private Long topicId;
    private String topicName;
    private int totalQuestions;
    private int attempted;
    private int correct;
    private int wrong;
    private double score;
    private LocalDateTime attemptedAt;

    public static QuizResultResponse fromEntity(QuizAttempt attempt, String topicName) {
        return new QuizResultResponse(
                attempt.getId(),
                attempt.getTopicId(),
                topicName,
                attempt.getTotalQuestions(),
                attempt.getAttempted(),
                attempt.getCorrect(),
                attempt.getWrong(),
                attempt.getScore(),
                attempt.getAttemptedAt()
        );
    }
}
