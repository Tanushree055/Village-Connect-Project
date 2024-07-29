package com.example.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrationServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String village = request.getParameter("dropdown");
        String password = request.getParameter("confirm-password");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (firstname, lastname, email, village_name, phone, password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, firstname);
                statement.setString(2, lastname);
                statement.setString(3, email);
                statement.setString(4, village);
                statement.setString(5, phone);
                statement.setString(6, password);
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);
                            HttpSession session = request.getSession();
                            session.setAttribute("userId", userId);
                            session.setAttribute("villageName", village);
                            System.out.println("A new user was inserted successfully with ID: " + userId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("villageData");
    }
}
