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


public class VillageDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VillageDataServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("villageName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String villageName = (String) session.getAttribute("villageName");
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM villages WHERE village_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, villageName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve data from resultSet and set as request attributes
                    	request.setAttribute("villageName", resultSet.getString("village_name"));
                    	request.setAttribute("population", resultSet.getInt("population"));
                    	request.setAttribute("male", resultSet.getInt("male")); // Example for male population
                    	request.setAttribute("female", resultSet.getInt("female")); // Example for female population
                    	request.setAttribute("children", resultSet.getInt("children"));
                    	request.setAttribute("populationDensity", resultSet.getString("population_density"));
                    	request.setAttribute("sexRatio", resultSet.getDouble("sex_ratio"));
                    	request.setAttribute("literacyRate", resultSet.getDouble("literacy_rate"));
                    	request.setAttribute("femaleLiteracyRate", resultSet.getDouble("female_literacy_rate"));
                    	request.setAttribute("illiterates", resultSet.getInt("illiterates"));
                    	request.setAttribute("workingPopulationPercentage", resultSet.getDouble("working_population_percentage"));
                    	request.setAttribute("district", resultSet.getString("district"));
                    	request.setAttribute("division", resultSet.getString("division"));
                    	String altitudeString = resultSet.getString("altitude");
                    	if (altitudeString != null && altitudeString.contains(" meters")) {
                    	    // Remove " meters" from altitude string
                    	    altitudeString = altitudeString.replace(" meters", "");
                    	}

                    	// Parse altitude as integer
                    	try {
                    	    int altitude = Integer.parseInt(altitudeString);
                    	    request.setAttribute("altitude", altitude);
                    	} catch (NumberFormatException e) {
                    	    // Handle parsing error if necessary
                    	    e.printStackTrace(); // Log or handle the exception appropriately
                    	}
                    	request.setAttribute("telephoneStdCode", resultSet.getString("telephone_std_code"));
                    	request.setAttribute("localLanguage", resultSet.getString("local_language"));
                    	request.setAttribute("pinCode", resultSet.getInt("pin_code"));
                    	request.setAttribute("nationalHighways", resultSet.getString("national_highways"));
                    	request.setAttribute("politics", resultSet.getString("politics"));
                    	request.setAttribute("schools", resultSet.getString("schools"));
                    	request.setAttribute("colleges", resultSet.getString("colleges"));
                    	request.setAttribute("healthCentresHospitals", resultSet.getString("health_centres_hospitals"));
                    	request.setAttribute("totalGeographicalArea", resultSet.getDouble("total_geographical_area"));
                    	request.setAttribute("agriculturalArea", resultSet.getDouble("agricultural_area"));
                    	request.setAttribute("irrigatedLand", resultSet.getDouble("irrigated_land"));
                    	request.setAttribute("nonAgriculturalArea", resultSet.getDouble("non_agricultural_area"));
                    	request.setAttribute("agriculturalCommodities", resultSet.getString("agricultural_commodities"));
                    	request.setAttribute("drinkingWater", resultSet.getString("drinking_water"));
                    	request.setAttribute("sanitation", resultSet.getString("sanitation"));
                    	request.setAttribute("postalHeadOffice", resultSet.getString("postal_head_office"));

                        // Add other attributes as needed
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("villageData.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

