package com.online.quiz.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.online.quiz.DatabaseConnectionFactory;
import java.sql.ResultSet;

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
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_cofirm = request.getParameter("confirm_password");
        String fullName = request.getParameter("fullName");
        String security = request.getParameter("security_ques");
        String answer = request.getParameter("answer");

        Connection con = DatabaseConnectionFactory.createConnection();
        int check = 0;
        try {
            Statement st1 = con.createStatement();
            String sql1 = "SELECT * FROM users where email='" + email + "';";

            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()) {
                check = 1;
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        request.setAttribute("checker", "none");
        try {
            Statement st2 = con.createStatement();
            String sql2 = "SELECT * FROM users where username='" + username + "';";

            ResultSet rs2 = st2.executeQuery(sql2);

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
                Statement st = con.createStatement();
                String sql = "INSERT INTO users values ('','" + email + "','" + username + "','" + password + "','" + fullName + "','"
                        + security + "','" + answer + "')";
                System.out.println(sql);
                st.executeUpdate(sql);
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
