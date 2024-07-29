package com.example.Servlet;

import java.io.IOException;
import com.example.dao.RatingDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RatingServlet
 */
public class RatingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RatingDAO ratingDAO = new RatingDAO();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/rating.html").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        // Read input from the request
        RatingRequest ratingRequest = mapper.readValue(request.getInputStream(), RatingRequest.class);
        
        // For this example, let's assume you have a way to get userId (like from session or request)
        int userId = 1; // Placeholder for userId; replace with actual logic to get the user ID
        
        // Submit or update the rating
        ratingDAO.submitOrUpdateRating(userId, ratingRequest.getRating());
        
        // Get average rating and total ratings
        double averageRating = ratingDAO.getAverageRating();
        int totalRatings = ratingDAO.getTotalRatings();
        
        // Create response object
        RatingResponse ratingResponse = new RatingResponse(averageRating, totalRatings);
        
        // Send JSON response
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(ratingResponse));
    }

    // Inner classes for request and response
    static class RatingRequest {
        private int rating;
        
        public int getRating() {
            return rating;
        }
        
        public void setRating(int rating) {
            this.rating = rating;
        }
    }

    static class RatingResponse {
        private double averageRating;
        private int totalRatings;

        public RatingResponse(double averageRating, int totalRatings) {
            this.averageRating = averageRating;
            this.totalRatings = totalRatings;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }

        public int getTotalRatings() {
            return totalRatings;
        }

        public void setTotalRatings(int totalRatings) {
            this.totalRatings = totalRatings;
        }
    }
}
