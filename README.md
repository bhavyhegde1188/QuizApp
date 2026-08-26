# Quiz Application — Backend

Spring Boot + Spring Data JPA + MySQL backend for the Quiz Management System.
No authentication, no PUT/DELETE — matches the agreed project scope.

## Run it

1. Create the database (or let `createDatabaseIfNotExist=true` do it for you):
   ```sql
   CREATE DATABASE quizapp;
   ```
2. Edit `src/main/resources/application.properties` with your MySQL username/password.
3. Build and run:
   ```bash
   mvn spring-boot:run
   ```
   The app starts on `http://localhost:8080`.

Tables (`topics`, `questions`, `quiz_attempts`, `quiz_answers`) are created automatically
via `spring.jpa.hibernate.ddl-auto=update`.

## API summary

| # | User  | Method | Endpoint                                   | Purpose                |
|---|-------|--------|---------------------------------------------|-------------------------|
| 1 | Admin | POST   | `/api/admin/topics`                        | Create topic            |
| 2 | Admin | GET    | `/api/admin/topics`                        | Get all topics          |
| 3 | Admin | POST   | `/api/admin/topics/{topicId}/questions`    | Add question to topic   |
| 4 | Admin | GET    | `/api/admin/topics/{topicId}/questions`    | Get topic questions     |
| 5 | User  | GET    | `/api/topics`                              | Display topics          |
| 6 | User  | GET    | `/api/quiz/topics/{topicId}/questions`     | Get quiz questions      |
| 7 | User  | POST   | `/api/quiz/submit`                         | Submit answers          |
| 8 | User  | GET    | `/api/quiz/result/{attemptId}`             | View result             |

## Sample requests

**Create a topic**
```json
POST /api/admin/topics
{ "name": "Java", "description": "Java Programming" }
```

**Add a question** (`POST /api/admin/topics/1/questions`)
```json
{
  "questionText": "Which keyword is used for inheritance?",
  "optionA": "implements",
  "optionB": "extends",
  "optionC": "super",
  "optionD": "inherit",
  "correctAnswer": "B"
}
```

**Submit a quiz** (`POST /api/quiz/submit`)
```json
{
  "userId": "user101",
  "topicId": 1,
  "answers": [
    { "questionId": 1, "selectedAnswer": "B" },
    { "questionId": 2, "selectedAnswer": "A" }
  ]
}
```
Response:
```json
{ "attemptId": 10, "totalQuestions": 2, "attempted": 2, "correct": 1, "wrong": 1, "score": 50.0 }
```

**Get result** (`GET /api/quiz/result/10`)
```json
{
  "attemptId": 10, "topicId": 1, "topicName": "Java",
  "totalQuestions": 2, "attempted": 2, "correct": 1, "wrong": 1,
  "score": 50.0, "attemptedAt": "2026-08-26T10:15:30"
}
```

## Notes on design decisions

- `QuestionResponse` (admin) includes `correctAnswer`; `QuizQuestionResponse` (user/Appzillon)
  deliberately omits it, so the quiz screen never leaks answers.
- Scoring happens entirely server-side in `QuizService.submitQuiz` — Appzillon only collects
  selections, it never computes the score.
- `QuizAttempt` → `QuizAnswer` is a cascading one-to-many, so a single `save()` on the attempt
  persists every answer row in one transaction.
- A skipped question (`selectedAnswer` null/blank) counts toward `totalQuestions` but not
  `attempted`, and is marked incorrect.
