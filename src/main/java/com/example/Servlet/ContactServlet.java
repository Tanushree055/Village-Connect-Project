package com.example.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.DatabaseConnection;

public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
       

        try (Connection connection = DatabaseConnection.getConnection()) {
            String verifyUserSQL = "SELECT id, firstname, lastname, email FROM users WHERE firstname = ? AND lastname = ? AND email = ?";
            try (PreparedStatement verifyUserStatement = connection.prepareStatement(verifyUserSQL)) {
                verifyUserStatement.setString(1, firstname);
                verifyUserStatement.setString(2, lastname);
                verifyUserStatement.setString(3, email);

                try (ResultSet resultSet = verifyUserStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("id");

                        String insertMessageSQL = "INSERT INTO contacts (user_id, message) VALUES (?, ?)";
                        try (PreparedStatement insertMessageStatement = connection.prepareStatement(insertMessageSQL)) {
                            insertMessageStatement.setInt(1, userId);
                            insertMessageStatement.setString(2, message);
                            insertMessageStatement.executeUpdate();

                            response.sendRedirect("contact.html?success=Message%20sent%20successfully");
                        }
                    } else {
                        response.sendRedirect("contact.html?error=Invalid%20credentials");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("contact.html?error=An%20error%20occurred");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to contact.html if accessed via GET
        response.sendRedirect("contact.html");
    }
}
