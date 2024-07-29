package com.example.Servlet;

import com.example.dao.ProblemDAO;
import com.example.model.Problem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProblemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int RECORDS_PER_PAGE = 3;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        HttpSession session = request.getSession();
        String villageName = (String) session.getAttribute("villageName");
        ProblemDAO problemDAO = new ProblemDAO();
        List<Problem> problems = problemDAO.getProblemsForVillage(villageName, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        int totalRecords = problemDAO.getProblemCountForVillage(villageName);

        int totalPages = (int) Math.ceil(totalRecords * 1.0 / RECORDS_PER_PAGE);

        request.setAttribute("problems", problems);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/problems.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ProblemDAO problemDAO = new ProblemDAO();
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if ("upvote".equals(action)) {
            int problemId = Integer.parseInt(request.getParameter("problemId"));
            problemDAO.upvoteProblem(userId, problemId);
        } else if ("downvote".equals(action)) {
            int problemId = Integer.parseInt(request.getParameter("problemId"));
            problemDAO.downvoteProblem(userId, problemId);
        } else if ("postProblem".equals(action)) {
            String subject = request.getParameter("subject");
            String description = request.getParameter("description");

            String firstname = (String) session.getAttribute("firstname");
            String lastname = (String) session.getAttribute("lastname");
            String email = (String) session.getAttribute("email");

            if (userId != null && firstname != null && lastname != null && email != null) {
                Problem problem = new Problem(userId, firstname, lastname, email, subject, description, 0);
                problemDAO.addProblem(problem);
            } else {
                System.out.println("User details not found in session");
            }
        }

        response.sendRedirect(request.getContextPath() + "/problems");
    }
}
