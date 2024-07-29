package com.example.dao;

import com.example.model.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {

	 public List<Problem> getProblemsForVillage(String villageName, int start, int total) {
	        List<Problem> problems = new ArrayList<>();
	        String query = "SELECT p.id, p.user_id, u.firstname, u.lastname, u.email, p.subject, p.description, p.upvotes " +
	                       "FROM problems p " +
	                       "JOIN users u ON p.user_id = u.id " +
	                       "WHERE u.village_name = ? LIMIT ?, ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, villageName);
	            statement.setInt(2, start);
	            statement.setInt(3, total);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Problem problem = new Problem(
	                        resultSet.getInt("id"),
	                        resultSet.getInt("user_id"),
	                        resultSet.getString("firstname"),
	                        resultSet.getString("lastname"),
	                        resultSet.getString("email"),
	                        resultSet.getString("subject"),
	                        resultSet.getString("description"),
	                        resultSet.getInt("upvotes")
	                );
	                problems.add(problem);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return problems;
	    }

	    public int getProblemCountForVillage(String villageName) {
	        int count = 0;
	        String query = "SELECT COUNT(*) FROM problems p JOIN users u ON p.user_id = u.id WHERE u.village_name = ?";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, villageName);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                count = resultSet.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return count;
	    }

	    public void addProblem(Problem problem) {
	        String query = "INSERT INTO problems (user_id, subject, description, upvotes) VALUES (?, ?, ?, ?)";
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, problem.getUserId());
	            statement.setString(2, problem.getSubject());
	            statement.setString(3, problem.getDescription());
	            statement.setInt(4, problem.getUpvotes());
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

//    public void upvoteProblem(int problemId) {
//        String sql = "UPDATE problems SET upvotes = upvotes + 1 WHERE id = ?";
//
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setInt(1, problemId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public boolean hasUserUpvoted(int userId, int problemId) {
        String query = "SELECT COUNT(*) FROM ProblemUpvotes WHERE userId = ? AND problemId = ? AND upvote = TRUE";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, problemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void upvoteProblem(int userId, int problemId) {
        if (!hasUserUpvoted(userId, problemId)) {
            String insertQuery = "INSERT INTO ProblemUpvotes (userId, problemId, upvote) VALUES (?, ?, TRUE)";
            String updateQuery = "UPDATE Problems SET upvotes = upvotes + 1 WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                insertStatement.setInt(1, userId);
                insertStatement.setInt(2, problemId);
                insertStatement.executeUpdate();

                updateStatement.setInt(1, problemId);
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void downvoteProblem(int userId, int problemId) {
        if (hasUserUpvoted(userId, problemId)) {
            String deleteQuery = "DELETE FROM ProblemUpvotes WHERE userId = ? AND problemId = ? AND upvote = TRUE";
            String updateQuery = "UPDATE Problems SET upvotes = upvotes - 1 WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                deleteStatement.setInt(1, userId);
                deleteStatement.setInt(2, problemId);
                deleteStatement.executeUpdate();

                updateStatement.setInt(1, problemId);
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
