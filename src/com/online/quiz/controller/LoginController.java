package com.online.quiz.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.online.quiz.DatabaseConnectionFactory;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/checkLogin")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * storing submitted
		 * username
		 * and 
		 * password 
		 * in variables 
		 */
		String username=request.getParameter("username");
		String password=request.getParameter("password");		
		
		//trying to connect to DB
		Connection con=DatabaseConnectionFactory.createConnection();		
		ResultSet set=null;
		int i=0;
		try
		{
		 Statement st=con.createStatement();
		 
		 //Searching for user in DB
		 String sql = "Select * from  users where username='"+username+"' and password='"+password+"' ";
		 		//System.out.println(sql);
		 
		 //executing query
		 set=st.executeQuery(sql);
		 String id = null;
		 while(set.next())
		 {
			 id = set.getString("id");
			 System.out.println(set.getString("id"));
			 i=1;
		 }
		
		 if(i!=0)
		{   
			 /*
			  * Creating Session
			  * and
			  * Storing UserID 
			  * in session variable
			  */
			 HttpSession session=request.getSession();
		     session.setAttribute("user",username);
		     session.setAttribute("userid",id);
			 RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/jsps/home.jsp");
			 rd.forward(request, response);
			 
		 }
		 else
		 {   
			 //otherwise displaying an error
			 request.setAttribute("errorMessage","Invalid username or password");
			 RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/jsps/login.jsp");
			 rd.forward(request, response);
		 }
		}catch(SQLException sqe){System.out.println("Error : While Fetching records from database");}
		try
		{
		 con.close();	
		}catch(SQLException se){System.out.println("Error : While Closing Connection");}
	}
		
		
	

}
