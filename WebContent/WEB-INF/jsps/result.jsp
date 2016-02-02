<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ page import="java.sql.*" %>
     <%@ page import="com.online.quiz.DatabaseConnectionFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"> 
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}

</style>
<title>Result</title>
</head>
<body>

<div style="position:absolute;left:500px;top:70px">
<h3 align="center">Quiz Result</h3>
<table border=1>
        <tr>
            <td class="head">
                Quiz :
            </td>
            <td>
                <span id="lblSubject">${sessionScope.exam}</span></td>
        </tr>
        <tr>
            <td class="head">
                Starting Time :
            </td>
            <td >
                <span id="lblStime">${sessionScope.started}</span></td>
        </tr>
        
              
                <tr>
            <td class="head">
               No. of Questions :
            </td>
            <td>
                <span id="lblNquestions">${sessionScope.totalNumberOfQuizQuestions}</span></td>
        </tr>
        
                <tr>
            <td class="head">
                No. of correct answers :
            </td>
            <td>
                <span id="lblNcans">${requestScope.result}</span></td>
        </tr>
        
    </table>

<%-- <c:if test="${requestScope.result >= 5}">
   <h3 align="center">Passed</h3>
</c:if> --%>
<%-- <c:if test="${requestScope.result < 5}">
   <h3 align="center">Failed</h3>
</c:if>
 --%>
 <!-- <h3 align="center">Passed</h3> -->
 <h3 align="center"><a href='${pageContext.request.contextPath}/exam/review'>Review Answers</a></h3><br>
<h3 align="center"><a href='${pageContext.request.contextPath}/'>Take Another Exam</a></h3><br>
<h3 align="center"><a href='${pageContext.request.contextPath}/'>Back to HomePage</a></h3>
</div>

<%
	Connection con=DatabaseConnectionFactory.createConnection();
	ResultSet rs;
	String username = (String)request.getSession().getAttribute("user");
	String examName = (String)request.getSession().getAttribute("examName");
	Integer score = (Integer)request.getAttribute("result");
	Statement st= con.createStatement();
       
	//String sql = "INSERT INTO scores VALUES('','"+ examName + "','"  + username + "'," + score + ");";
			//System.out.println();
                        String sql = "Select * from scores where exam='" + examName + "' AND username='" + username + "';";
	rs = st.executeQuery(sql);
        int i=0;
        while(rs.next()){
            i+=1;
        }
        
        if(i==0){
            String sql1 = "INSERT INTO scores VALUES('','"+ examName + "','"  + username + "'," + score + ");";
            st.executeUpdate(sql1);
        }
        else{
            String sql1 = "UPDATE scores SET score='" + score + "' where username='" + username + "';";
            st.executeUpdate(sql1);
        }

	
%>
<%-- ${sessionScope.userid} --%>

</body>
</html>