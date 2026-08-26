package com.example.quizapp.controller;

import com.example.quizapp.dto.TopicResponse;
import com.example.quizapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** User-facing endpoint consumed by Appzillon's topic-selection screen. */
@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    // GET /api/topics
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }
}
