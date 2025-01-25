
QuestionService.java (Database Service for Questions)

package services;

import models.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    public static List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions WHERE quiz_id = ?")) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option").charAt(0)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
