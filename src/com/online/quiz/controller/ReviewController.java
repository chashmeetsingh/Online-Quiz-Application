package com.online.quiz.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.online.quiz.Exam;
import com.online.quiz.QuizQuestion;

/**
 * Servlet implementation class ReviewController
 */
@WebServlet("/exam/review")
public class ReviewController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Exam exam = (Exam) request.getSession().getAttribute("currentExam");

        request.setAttribute("totalQuestion", exam.getTotalNumberOfQuestions());

        ArrayList<QuizQuestion> reviewQuestionList = new ArrayList<QuizQuestion>();

        Document dom = exam.getDom();

        //Displaying Answers
        for (int i = 0; i < exam.getTotalNumberOfQuestions(); i++) {
            String options[] = new String[4];
            String question = null;
            int correct = 0;

            NodeList qList = dom.getElementsByTagName("question");
            NodeList childList = qList.item(i).getChildNodes();

            int counter = 0;

            for (int j = 0; j < childList.getLength(); j++) {
                Node childNode = childList.item(j);
                if ("answer".equals(childNode.getNodeName())) {
                    options[counter] = childList.item(j).getTextContent();
                    counter++;
                } else if ("quizquestion".equals(childNode.getNodeName())) {
                    question = childList.item(j).getTextContent();
                } else if ("correct".equals(childNode.getNodeName())) {
                    correct = Integer.parseInt(childList.item(j).getTextContent());
                }

            }

            QuizQuestion q = new QuizQuestion();
            q.setQuestionNumber(i);
            q.setQuestion(question);
            q.setCorrectOptionIndex(correct);
            q.setQuestionOptions(options);
            q.setUserSelected(exam.getUserSelectionForQuestion(i));
            reviewQuestionList.add(i, q);
        }
        request.setAttribute("reviewQuestions", reviewQuestionList);
        request.getRequestDispatcher("/WEB-INF/jsps/examReview2.jsp").forward(request, response);
    }


}
