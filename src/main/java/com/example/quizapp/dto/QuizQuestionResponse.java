package com.example.quizapp.dto;

import com.example.quizapp.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used on the USER-facing quiz endpoint. Deliberately excludes
 * correctAnswer so Appzillon never receives it before submission.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionResponse {

    private Long questionId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public static QuizQuestionResponse fromEntity(Question q) {
        return new QuizQuestionResponse(
                q.getId(),
                q.getQuestionText(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD()
        );
    }
}
