<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page import="java.sql.*" %>
     <%@ page import="com.online.quiz.DatabaseConnectionFactory" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">   
    <style type="text/css">
    
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}

.button {
	padding: 10px 15px;
	font-size: 14px;
	line-height: 100%;
	text-shadow: 0 1px rgba(0, 0, 0, 0.4);
	color: #000;
	
	vertical-align: middle;
	text-align: center;
	cursor: pointer;
	font-weight: bold;
	transition: background 0.1s ease-in-out;
	-webkit-transition: background 0.1s ease-in-out;
	-moz-transition: background 0.1s ease-in-out;
	-ms-transition: background 0.1s ease-in-out;
	-o-transition: background 0.1s ease-in-out;
	text-shadow: 0 1px rgba(0, 0, 0, 0.3);
	color: #000;
	-webkit-border-radius: 40px;
	-moz-border-radius: 40px;
	border-radius: 40px;
	font-family: 'Helvetica Neue', Helvetica, sans-serif;
}

.button, .button:hover, .button:active {
	outline: 0 none;
	text-decoration: none;
        color: #fff;
}

.username {
	background-color: #2ecc71;
	box-shadow: 0px 3px 0px 0px #239a55;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    padding: 12px 16px;
    z-index: 1;
}

.dropdown:hover .dropdown-content {
    display: block;
}

</style>
  
   <title>Online Quiz</title>
</head>
<body>

<div id='cssmenu'>
<ul>
   <li class=''><a href='${pageContext.request.contextPath}/'><span>Home</span></a></li>
   <c:if test='${empty sessionScope.user }'>
   	<li><a href='${pageContext.request.contextPath}/login'><span>Login</span></a></li>
   	<li><a href='${pageContext.request.contextPath}/register'><span>Register</span></a></li>
   </c:if>
   <c:if test='${not empty sessionScope.user}'>
   <li>
	   <div class="dropdown">
		  <a href="#"><span>All Tests</span></a>
		  <div class="dropdown-content">
		    
			   <c:forEach items="${array}" var="array">
				    <tr>      
				        <a href="takeExam?test=${array.getString("title")}&id=${array.getString("id")}" style="color:black;">${array.getString("title")}</a>
				    </tr>
				</c:forEach>
		   
		    
		  </div>
		</div>
	</li>
	</c:if>
	<c:if test='${not empty sessionScope.user}'>
		<li><a href='${pageContext.request.contextPath}/logout'>Logout</a></li>
	</c:if>
</ul>
</div>

<c:if test='${not empty sessionScope.user}'>

<div style="position:absolute;top:70px;left:1100px">
Logged in as <a href="#" class="button username">${sessionScope.user}</a>
</div>


</c:if>

<div style="margin-left:20%;margin-right:20%;margin-top:30px;">
<%--  <table cellpadding="0" cellspacing="50">

<tr>
<td><a href="takeExam?test=java"><img height="200" width="200" src="${pageContext.request.contextPath}/images/java.png"/></a></td>
<td><a href="takeExam?test=javascript"><img height="200" width="200" src="${pageContext.request.contextPath}/images/javascript.png"/></a></td>
<td><a href="takeExam?test=sql"><img height="200" width="200" src="${pageContext.request.contextPath}/images/sql-logo.png"/></a></td>
<td><a href="takeExam?test=python"><img height="200" width="200" src="${pageContext.request.contextPath}/images/python.jpg"/></a></td>
</tr>

<tr>
<td><a href="takeExam?test=css"><img height="200" width="200" src="${pageContext.request.contextPath}/images/css.jpg"/></a></td>
<td><a href="takeExam?test=php"><img height="200" width="200" src="${pageContext.request.contextPath}/images/php-logo.jpg"/></a></td>
<td><a href="takeExam?test=linux"><img height="200" width="200" src="${pageContext.request.contextPath}/images/logo-linux.png"/></a></td>
<td><a href="takeExam?test=mongodb"><img height="200" width="200" src="${pageContext.request.contextPath}/images/mongodb_logo.png"/></a></td>
</tr>

</table>  --%>
<c:if test='${not empty sessionScope.user}'>
	<h1>Welcome ${sessionScope.user}</h1>
	
	
	<table class="table table-striped table-bordered" width="100%" style="margin-top:50px;">
	  <thead>
	    <tr>
	      <th>Test Name</th>
	      <th>Score</th>
	    </tr>
	  </thead>
	  <tbody>
	  
	  <%
	  
	    Connection con=DatabaseConnectionFactory.createConnection();
	    
	    
	    	ResultSet rs;
		 	Statement st=con.createStatement();
		 	//System.out.println(request.);
		 	String sql = "select * from scores where username='" + request.getSession().getAttribute("user") + "' ORDER BY id DESC;";
		 	System.out.println(sql);
		 	rs = st.executeQuery(sql);
		 	
		%>
		 	<% while(rs.next()) {%>
			
			<tr>
				<td><%= rs.getString("exam") %></td>
      			<td><%= rs.getString("score") %></td>
			</tr>
	  		<% } %>
	  
	  	
	  </tbody>
	</table>
	
	
	
</c:if>
<c:if test='${empty sessionScope.user}'>
	<h1>Welcome to Online Quiz Application</h1>

</div>
</c:if>


</body>
</html>
