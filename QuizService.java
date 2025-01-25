QuizService.java (Database Service for Quizzes)

package services;

import models.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService {

    public static List<String> getAllQuizTitles() {
        List<String> quizTitles = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT title FROM quizzes")) {
            while (rs.next()) {
                quizTitles.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizTitles;
    }

    public static int getQuizIdByTitle(String title) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM quizzes WHERE title = ?")) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
