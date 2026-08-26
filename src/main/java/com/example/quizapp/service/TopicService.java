package com.example.quizapp.service;

import com.example.quizapp.dto.TopicRequest;
import com.example.quizapp.dto.TopicResponse;
import com.example.quizapp.entity.Topic;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicResponse createTopic(TopicRequest request) {
        Topic topic = new Topic();
        topic.setName(request.getName());
        topic.setDescription(request.getDescription());
        Topic saved = topicRepository.save(topic);
        return TopicResponse.fromEntity(saved);
    }

    public List<TopicResponse> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(TopicResponse::fromEntity)
                .toList();
    }

    /** Used internally by other services; throws if the topic does not exist. */
    public Topic getTopicEntityOrThrow(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
    }
}
