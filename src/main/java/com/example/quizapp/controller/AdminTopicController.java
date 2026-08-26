package com.example.quizapp.controller;

import com.example.quizapp.dto.TopicRequest;
import com.example.quizapp.dto.TopicResponse;
import com.example.quizapp.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Admin-only endpoints. Consumed via Postman — there is no Appzillon UI for these. */
@RestController
@RequestMapping("/api/admin/topics")
@RequiredArgsConstructor
public class AdminTopicController {

    private final TopicService topicService;

    // POST /api/admin/topics
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest request) {
        TopicResponse created = topicService.createTopic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/admin/topics
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }
}
