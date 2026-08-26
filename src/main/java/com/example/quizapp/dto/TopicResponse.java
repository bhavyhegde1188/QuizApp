package com.example.quizapp.dto;

import com.example.quizapp.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private String name;
    private String description;

    public static TopicResponse fromEntity(Topic topic) {
        return new TopicResponse(topic.getId(), topic.getName(), topic.getDescription());
    }
}
