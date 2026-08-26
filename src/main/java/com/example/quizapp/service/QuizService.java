package com.example.quizapp.service;

import com.example.quizapp.dto.QuizResultResponse;
import com.example.quizapp.dto.QuizSubmitRequest;
import com.example.quizapp.dto.QuizSubmitResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.QuizAnswer;
import com.example.quizapp.entity.QuizAttempt;
import com.example.quizapp.entity.Topic;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizAnswerRepository;
import com.example.quizapp.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final TopicService topicService;

    @Transactional
    public QuizSubmitResponse submitQuiz(QuizSubmitRequest request) {
        // 1. Make sure the topic exists.
        Topic topic = topicService.getTopicEntityOrThrow(request.getTopicId());

        // 2. Build the attempt shell first so we can attach answers to it.
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUserId(request.getUserId());
        attempt.setTopicId(topic.getId());

        int totalQuestions = request.getAnswers().size();
        int attemptedCount = 0;
        int correctCount = 0;

        List<QuizAnswer> answerEntities = new ArrayList<>();

        // 3. Evaluate every submitted answer against the stored correct answer.
        for (QuizSubmitRequest.AnswerSubmission submitted : request.getAnswers()) {
            Question question = questionRepository.findById(submitted.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Question not found with id: " + submitted.getQuestionId()));

            String selected = submitted.getSelectedAnswer() == null
                    ? null
                    : submitted.getSelectedAnswer().trim().toUpperCase();

            boolean wasAttempted = selected != null && !selected.isBlank();
            boolean isCorrect = wasAttempted && selected.equalsIgnoreCase(question.getCorrectAnswer());

            if (wasAttempted) {
                attemptedCount++;
            }
            if (isCorrect) {
                correctCount++;
            }

            QuizAnswer answerEntity = new QuizAnswer();
            answerEntity.setAttempt(attempt);
            answerEntity.setQuestion(question);
            answerEntity.setSelectedAnswer(selected);
            answerEntity.setCorrectAnswer(question.getCorrectAnswer());
            answerEntity.setCorrect(isCorrect);
            answerEntities.add(answerEntity);
        }

        int wrongCount = attemptedCount - correctCount;
        double score = totalQuestions == 0 ? 0.0 : (correctCount * 100.0) / totalQuestions;

        attempt.setTotalQuestions(totalQuestions);
        attempt.setAttempted(attemptedCount);
        attempt.setCorrect(correctCount);
        attempt.setWrong(wrongCount);
        attempt.setScore(score);
        attempt.setAnswers(answerEntities);

        // 4. Persist the attempt; cascade saves every QuizAnswer with it.
        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);

        return new QuizSubmitResponse(
                savedAttempt.getId(),
                totalQuestions,
                attemptedCount,
                correctCount,
                wrongCount,
                score
        );
    }

    public QuizResultResponse getResult(Long attemptId) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz attempt not found with id: " + attemptId));

        Topic topic = topicService.getTopicEntityOrThrow(attempt.getTopicId());

        return QuizResultResponse.fromEntity(attempt, topic.getName());
    }

    /** Latest attempt made on a given topic (most recent attemptedAt), across all users. */
    public QuizResultResponse getLatestResult(Long topicId) {
        // Ensure the topic itself exists so a typo'd topicId gives a clear 404.
        Topic topic = topicService.getTopicEntityOrThrow(topicId);

        QuizAttempt attempt = quizAttemptRepository
                .findFirstByTopicIdOrderByAttemptedAtDesc(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No quiz attempt found for topicId: " + topicId));

        return QuizResultResponse.fromEntity(attempt, topic.getName());
    }
}
