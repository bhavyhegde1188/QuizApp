package com.example.quizapp.controller;

import com.example.quizapp.dto.QuizQuestionResponse;
import com.example.quizapp.dto.QuizResultResponse;
import com.example.quizapp.dto.QuizSubmitRequest;
import com.example.quizapp.dto.QuizSubmitResponse;
import com.example.quizapp.service.QuestionService;
import com.example.quizapp.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** User-facing endpoints consumed by Appzillon during a quiz attempt. */
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuestionService questionService;
    private final QuizService quizService;

    // GET /api/quiz/topics/{topicId}/questions
    // Correct answers are never included here — see QuizQuestionResponse.
    @GetMapping("/topics/{topicId}/questions")
    public ResponseEntity<List<QuizQuestionResponse>> getQuizQuestions(@PathVariable Long topicId) {
        return ResponseEntity.ok(questionService.getQuestionsForQuiz(topicId));
    }

    // POST /api/quiz/submit
    @PostMapping("/submit")
    public ResponseEntity<QuizSubmitResponse> submitQuiz(@Valid @RequestBody QuizSubmitRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(request));
    }

    // GET /api/quiz/result/{attemptId}
    @GetMapping("/result/{attemptId}")
    public ResponseEntity<QuizResultResponse> getResult(@PathVariable Long attemptId) {
        return ResponseEntity.ok(quizService.getResult(attemptId));
    }

    // GET /api/quiz/result/latest/{topicId}
    // Note: mapped as literal "/result/latest/..." segment, so Spring resolves it
    // ahead of the "/result/{attemptId}" pattern above — no path-variable clash.
    @GetMapping("/result/latest/{topicId}")
    public ResponseEntity<QuizResultResponse> getLatestResult(@PathVariable Long topicId) {
        return ResponseEntity.ok(quizService.getLatestResult(topicId));
    }
}
