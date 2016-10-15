package com.online.quiz.controller;

import com.online.quiz.DatabaseConnectionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/checkRegister")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String security = request.getParameter("security_ques");
        String answer = request.getParameter("answer");

        Connection con = DatabaseConnectionFactory.createConnection();
        int check = 0;
        try {
            PreparedStatement st1 = con.prepareStatement("SELECT * FROM users where email=?");
            st1.setString(1, email);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()) {
                check = 1;
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        request.setAttribute("checker", "none");
        try {
            PreparedStatement st2 = con.prepareStatement("SELECT * FROM users where username=?");
            st2.setString(1, username);
            ResultSet rs2 = st2.executeQuery();

            while (rs2.next()) {
                check = 2;
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        if (check != 0) {
            if (check == 1) {
                request.getSession().setAttribute("checker", "email");
            }
            if (check == 2) {
                request.getSession().setAttribute("checker", "username");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsps/register.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                PreparedStatement st = con.prepareStatement("INSERT INTO users values ('',? , ?, ?, ?, ?, ?)");
                st.setString(1, email);
                st.setString(2, username);
                st.setString(3, password);
                st.setString(4, fullName);
                st.setString(5, security);
                st.setString(6, answer);
                st.executeUpdate();
            } catch (SQLException sqe) {
                sqe.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException se) {
                System.out.println("Error : While Closing Connection");
            }
            request.setAttribute("newUser", username);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsps/regSuccess.jsp");
            dispatcher.forward(request, response);
        }
    }
}
