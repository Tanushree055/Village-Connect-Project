package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingDAO {

    // Insert a new rating or update existing rating
    public void submitOrUpdateRating(int userId, int rating) {
        String query = "INSERT INTO ratings (user_id, rating) VALUES (?, ?) "
                     + "ON DUPLICATE KEY UPDATE rating = VALUES(rating), timestamp = CURRENT_TIMESTAMP";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, rating);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get average rating
    public double getAverageRating() {
        String query = "SELECT AVG(rating) FROM ratings";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get total number of ratings
    public int getTotalRatings() {
        String query = "SELECT COUNT(*) FROM ratings";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get user's current rating
    public Integer getUserRating(int userId) {
        String query = "SELECT rating FROM ratings WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No rating found for the user
    }
}
