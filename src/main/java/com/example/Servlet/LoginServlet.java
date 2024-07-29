package com.example.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.DatabaseConnection;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("confirm-password");
        HttpSession session = request.getSession();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, firstname, lastname, email, village_name FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("id");
                        String firstname = resultSet.getString("firstname");
                        String lastname = resultSet.getString("lastname");
                        String emailFromDB = resultSet.getString("email");
                        String villageName = resultSet.getString("village_name");

                        session.setAttribute("userId", userId);
                        session.setAttribute("firstname", firstname);
                        session.setAttribute("lastname", lastname);
                        session.setAttribute("email", emailFromDB);
                        session.setAttribute("villageName", villageName);

                        // Redirect to home.html after successful login
                        response.sendRedirect("home.html");
                    } else {
                        // Invalid credentials
                        response.sendRedirect("login.html?error=Invalid%20credentials");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=An%20error%20occurred");
        }
    }
}

