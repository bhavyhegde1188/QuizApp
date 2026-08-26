package com.example.quizapp.controller;

import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.dto.QuestionResponse;
import com.example.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Admin-only endpoints. Consumed via Postman — there is no Appzillon UI for these. */
@RestController
@RequestMapping("/api/admin/topics/{topicId}/questions")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final QuestionService questionService;

    // POST /api/admin/topics/{topicId}/questions
    @PostMapping
    public ResponseEntity<QuestionResponse> addQuestion(
            @PathVariable Long topicId,
            @Valid @RequestBody QuestionRequest request) {
        QuestionResponse created = questionService.addQuestion(topicId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/admin/topics/{topicId}/questions
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getQuestionsByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(questionService.getQuestionsForAdmin(topicId));
    }
}
