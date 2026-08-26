package com.example.quizapp.dto;

import com.example.quizapp.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used ONLY on admin endpoints. Includes the correct answer so the admin
 * can verify what was stored. Never expose this DTO to Appzillon/user APIs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private Long topicId;

    public static QuestionResponse fromEntity(Question q) {
        return new QuestionResponse(
                q.getId(),
                q.getQuestionText(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD(),
                q.getCorrectAnswer(),
                q.getTopic().getId()
        );
    }
}
