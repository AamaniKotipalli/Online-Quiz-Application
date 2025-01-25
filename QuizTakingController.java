QuizTakingController.java

package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Question;
import services.QuizService;
import services.QuestionService;
import java.util.List;

public class QuizTakingController {

    @FXML
    private ComboBox<String> quizSelectionBox;

    @FXML
    private Label questionLabel, feedbackLabel, scoreLabel;

    @FXML
    private RadioButton optionA, optionB, optionC, optionD;

    private ToggleGroup optionsGroup;

    private List<Question> currentQuestions;
    private int currentIndex = 0;
    private int score = 0;

    @FXML
    public void initialize() {
        optionsGroup = new ToggleGroup();
        optionA.setToggleGroup(optionsGroup);
        optionB.setToggleGroup(optionsGroup);
        optionC.setToggleGroup(optionsGroup);
        optionD.setToggleGroup(optionsGroup);

        loadAvailableQuizzes();
    }

    private void loadAvailableQuizzes() {
        List<String> quizTitles = QuizService.getAllQuizTitles();
        quizSelectionBox.getItems().addAll(quizTitles);
    }

    @FXML
    private void handleStartQuiz() {
        String selectedQuiz = quizSelectionBox.getValue();
        if (selectedQuiz != null) {
            int quizId = QuizService.getQuizIdByTitle(selectedQuiz);
            currentQuestions = QuestionService.getQuestionsByQuizId(quizId);
            if (!currentQuestions.isEmpty()) {
                currentIndex = 0;
                score = 0;
                displayQuestion();
                feedbackLabel.setText("");
                scoreLabel.setText("Score: 0");
            } else {
                showAlert(Alert.AlertType.WARNING, "No Questions", "No questions found for this quiz.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a quiz.");
        }
    }

    private void displayQuestion() {
        if (currentIndex < currentQuestions.size()) {
            Question question = currentQuestions.get(currentIndex);
            questionLabel.setText(question.getQuestionText());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());
            optionsGroup.selectToggle(null);
        } else {
            questionLabel.setText("Quiz Completed!");
        }
    }

    @FXML
    private void handleSubmitAnswer() {
        if (currentIndex < currentQuestions.size()) {
            Question question = currentQuestions.get(currentIndex);
            RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
            
            if (selectedOption != null) {
                char selectedAnswer = selectedOption.getText().charAt(0);
                if (selectedAnswer == question.getCorrectOption()) {
                    score++;
                    feedbackLabel.setText("Correct!");
                } else {
                    feedbackLabel.setText("Incorrect! The correct answer is: " + question.getCorrectOption());
                }

                scoreLabel.setText("Score: " + score);
                currentIndex++;
                displayQuestion();
            } else {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an option.");
            }
        }
    }

    @FXML
    private void handleFinishQuiz() {
        showAlert(Alert.AlertType.INFORMATION, "Quiz Finished", "Your final score is: " + score);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}













