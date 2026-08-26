package com.example.quizapp.service;

import com.example.quizapp.dto.QuestionRequest;
import com.example.quizapp.dto.QuestionResponse;
import com.example.quizapp.dto.QuizQuestionResponse;
import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Topic;
import com.example.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicService topicService;

    public QuestionResponse addQuestion(Long topicId, QuestionRequest request) {
        Topic topic = topicService.getTopicEntityOrThrow(topicId);

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer().toUpperCase());
        question.setTopic(topic);

        Question saved = questionRepository.save(question);
        return QuestionResponse.fromEntity(saved);
    }

    /** Admin view — includes correct answers. */
    public List<QuestionResponse> getQuestionsForAdmin(Long topicId) {
        topicService.getTopicEntityOrThrow(topicId); // ensure topic exists
        return questionRepository.findByTopicId(topicId)
                .stream()
                .map(QuestionResponse::fromEntity)
                .toList();
    }

    /** User/Appzillon view — correct answers are stripped out. */
    public List<QuizQuestionResponse> getQuestionsForQuiz(Long topicId) {
        topicService.getTopicEntityOrThrow(topicId); // ensure topic exists
        return questionRepository.findByTopicId(topicId)
                .stream()
                .map(QuizQuestionResponse::fromEntity)
                .toList();
    }
}
