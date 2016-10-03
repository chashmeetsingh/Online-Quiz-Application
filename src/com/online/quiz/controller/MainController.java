package com.online.quiz.controller;

import java.io.IOException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.online.quiz.CreateDOM;
import com.online.quiz.DatabaseConnectionFactory;

/*
 * Main Controller Class
 * For Routing
 */
@WebServlet(urlPatterns = {"/login", "/register", "/takeExam", "/logout"})
public class MainController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    ArrayList<JSONObject> array;
    ResultSet rs;
    Integer count = 0;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String applicationContextPath = request.getContextPath();

        Connection con = DatabaseConnectionFactory.createConnection();
        try {
            Statement st = con.createStatement();
            String count_query = "SELECT DISTINCT id,title FROM exams;";
            rs = st.executeQuery(count_query);
            array = new ArrayList<JSONObject>();

            while (true) {
                if (rs.next()) {
                    System.out.println(rs.getString("title"));
                    System.out.println(rs.getString("id"));
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("title", rs.getString("title"));
                    jsonObject.put("id", rs.getString("id"));
                    array.add(jsonObject);
                } else {
                    break;
                }
            }
            System.out.println(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("array", array);

        //Send to Home Page
        if (request.getRequestURI().equals(applicationContextPath + "/")) {
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/WEB-INF/jsps/home.jsp");
            dispatcher.forward(request, response);
        } //Send to login page
        else if (request.getRequestURI().equals(
                applicationContextPath + "/login")) {
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/WEB-INF/jsps/login.jsp");
            dispatcher.forward(request, response);
        } //Send to registration page
        else if (request.getRequestURI().equals(
                applicationContextPath + "/register")) {
            request.getSession().setAttribute("checker", "none");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/WEB-INF/jsps/register.jsp");
            dispatcher.forward(request, response);
        } //Send to Exam instructions page
        else if (request.getRequestURI().equals(
                applicationContextPath + "/takeExam")) {

            //Setting attributes For Usage
            request.getSession().setAttribute("currentExam", null);
            request.getSession().setAttribute("totalNumberOfQuizQuestions", null);
            request.getSession().setAttribute("quizDuration", null);
            request.getSession().setAttribute("min", null);
            request.getSession().setAttribute("sec", null);

            String exam = request.getParameter("test");
            request.getSession().setAttribute("exam", exam);
            String id = request.getParameter("id");
            request.getSession().setAttribute("id", id);

            //Checking is User Session is null
            //Redirect to Login Page
            if (request.getSession().getAttribute("user") == null) {
                request.getRequestDispatcher("/login").forward(request,
                        response);

            } else {
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher("/WEB-INF/jsps/quizDetails.jsp");
                Document document = null;
                try {
                    document = CreateDOM.getDOM(exam, id);

                    request.getSession().setAttribute("totalNumberOfQuizQuestions", document.getElementsByTagName("totalquizquestions").item(0).getTextContent());
                    request.getSession().setAttribute("quizDuration", document.getElementsByTagName("quizduration").item(0).getTextContent());
                    request.getSession().setAttribute("min", document.getElementsByTagName("quizduration").item(0).getTextContent());
                    request.getSession().setAttribute("sec", 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                dispatcher.forward(request, response);
            }
        } //Loggin Out
        else if (request.getRequestURI().equals(
                applicationContextPath + "/logout")) {
            request.getSession().invalidate();
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/WEB-INF/jsps/home.jsp");
            dispatcher.forward(request, response);
        }

    }

}
