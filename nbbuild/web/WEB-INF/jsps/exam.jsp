<%@ page language="java" import="com.online.quiz.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question</title>
 <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}
</style>
<script language ="javascript" >
        var tim;       
        var min = '${sessionScope.min}';
        var sec = '${sessionScope.sec}';
        
        <% String check = request.getSession().getAttribute("selectedoption").toString(); %>
       
        function submit(id){
        	document.questionForm.minute.value = min;   
       	 	document.questionForm.second.value = sec; 
       	 	document.questionForm.link.value = id;
       	 	document.questionForm.submit();
        }
        
        function markSubmit(){
        	document.questionForm.minute.value = min;   
	       	 document.questionForm.second.value = sec;
	       	 document.questionForm.mark.value = "0";
	       	 document.questionForm.submit();
        }
        
        function unmarkSubmit(){
        	document.questionForm.minute.value = min;   
	       	 document.questionForm.second.value = sec;
	       	 document.questionForm.mark.value = "";
	       	 document.questionForm.submit();
        }
        
        function radioCheck(){
        	if(<%= check %> != "-1")
	        	document.getElementById(<%= check %>).checked = true;
        }
         
        function clearIt(){
        	document.questionForm.minute.value = min;   
       	 	document.questionForm.second.value = sec;
       	 	document.questionForm.clear.value = "clear";
       	 	document.questionForm.submit();
        } 
        
        function customSubmit(someValue){  
        	 document.questionForm.minute.value = min;   
        	 document.questionForm.second.value = sec;
        	 document.questionForm.submit();  
        	 }  
			
        function examTimer() {
            if (parseInt(sec) >0) {
			
			    document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";
                sec = parseInt(sec) - 1;                
                tim = setTimeout("examTimer()", 1000);
            }
            else {
			
			    if (parseInt(min)==0 && parseInt(sec)==0){
			    	document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";
				     alert("Time Up");
				     document.questionForm.minute.value=0;
				     document.questionForm.second.value=0;
				     document.questionForm.submit();
					 
			     }
				 
                if (parseInt(sec) == 0) {				
				    document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";					
                    min = parseInt(min) - 1;
					sec=59;
                    tim = setTimeout("examTimer()", 1000);
                }
               
            }
        }
    </script>

</head><br/>
<style>
#content{
     /* height: 100%; */
     margin-left:10px;
     margin-top:20px;
/*    //overflow:auto;*/
    overflow-y: scroll;
     width:3%;
     text-align: center;
 }
</style>
<body onload="examTimer(),radioCheck()">

<div style="position: fixed;left: 30%;margin-left: -50px;">
	Answered: <%= request.getSession().getAttribute("answercount") %>&nbsp;&nbsp;&nbsp;
	Unanswered: <%= Integer.parseInt(request.getSession().getAttribute("totalNumberOfQuizQuestions").toString()) -
					Integer.parseInt(request.getSession().getAttribute("answercount").toString())	 %>&nbsp;&nbsp;&nbsp;
	Marked For Review: <%= request.getSession().getAttribute("markcount") %>
</div>

<div id="content" style="border:1px solid black;">
	<% ArrayList<String> marks = (ArrayList<String>) request.getSession().getAttribute("mark"); %>
   	<% for(int i=0;i<marks.size();i++) { %>
   	<div style="margin-top:5px;">
   		<% if(marks.get(i) == "0") {%>
   			<a href="#" onclick="submit(<%= i+1 %>)"><%= i+1 %>.<img width="50%" height="50%" src="${pageContext.request.contextPath}/images/red.png"></a><br><br>
   		<% } %>
   		<% if(marks.get(i) != "0") {%>
   			<a href="#" onclick="submit(<%= i+1 %>)"><%= i+1 %>.</a><br><br>
   		<% } %>
   		<hr>
   	</div>
   	<% } %>
</div>



<div style="position:fixed;left:50px;top:20px;">
<%
  int currentQuestion=((Exam)request.getSession().getAttribute("currentExam")).getCurrentQuestion();
 // System.out.println("Question Number "+currentQuestion+ " retrieved ");
 %>
&nbsp;&nbsp;&nbsp;Current Question ${sessionScope.quest.questionNumber+1} / ${sessionScope.totalNumberOfQuizQuestions}

</div>

<div id="showtime" style="position:fixed;left:800px;top:20px"></div>

 <div style="position:fixed;width:1000px;padding:25px;
  height: 200px;border: 1px solid green ;left:100px;top:60px">
<span>${sessionScope.quest.question}</span><br/><br/>



<form action="exam" method="get" name="questionForm" >

 <c:forEach var="choice" items="${sessionScope.quest.questionOptions}" varStatus="counter">
 <input type="radio" name="answer" value="${counter.count}" id="${ counter.count }">${choice}  <br/>
 </c:forEach> <br/> 
 
 
 <c:if test="${sessionScope.quest.questionNumber > 0}">
 <input type="submit" name="action" value="Previous" onclick="customSubmit()" />
 </c:if>
 
 <c:if test="${sessionScope.quest.questionNumber < sessionScope.totalNumberOfQuizQuestions-1}">
 <input type="submit" name="action" value="Next" onclick="customSubmit()" />
 </c:if> 
 
 <input type="submit" name="clear" value="Clear" onclick="clearIt()" />
 <% ArrayList<String> mark1 = (ArrayList<String>) request.getSession().getAttribute("mark"); %>
<%-- <%= mark1 %> --%>
<% if (mark1.get(Integer.parseInt(request.getSession().getAttribute("current").toString())) == "-1") {%>
	 <input type="submit" name="mark" value="Mark" onclick="markSubmit()" />
<% } %>
<% if (mark1.get(Integer.parseInt(request.getSession().getAttribute("current").toString())) == "0") {%>
	<input type="submit" name="unmark" value="Unmark" onclick="unmarkSubmit()" />
<% } %>	
	
 <input type="submit" name="action" value="Finish Exam" onclick="customSubmit()" />
 
<input type="hidden" name="minute"/> 
<input type="hidden" name="second"/>
<input type="hidden" name="link" />
<input type="hidden" name="mark" />

</form>


</div>
	

</body>
</html>