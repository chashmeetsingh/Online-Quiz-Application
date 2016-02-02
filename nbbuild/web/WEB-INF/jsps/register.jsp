<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <title>
        Register
    </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}
.user-icon {
	top:153px; /* Positioning fix for slide-in, got lazy to think up of simpler method. */
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/images/user-icon.png') no-repeat center;	
}

.pass-icon {
	top:201px;
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/images/pass-icon.png') no-repeat center;
}


</style>
</head>

<script>
     <% String checker = request.getSession().getAttribute("checker").toString(); %>
    function validate(){  
        var form = document.getElementById("form");
        //alert(form['email'].value);
        if(form['email'].value.match(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/)){
            if(form['username'].value.length >= 5 ){
                //alert(form['password'].value);
                //alert(form['confirm_password'].value);
                if(form['password'].value == form['confirm_password'].value && form['password'].value.length >= 8){
                    
                    if(form['answer'].value.length > 1 ){
                        return true;
                    }
                    else{
                        alert("Answer cannot be empty!");
                        return false;
                    }
                }
                else{
                    alert("Passwords Do not Match or less than 8 chars");
                    return false;
                }
            }
            else{
                alert("Username should be 5 chars long");
                return false;
            }
        }
        else{
            alert("Invalid or Email Not Provided");
            return false;
        }
    }
    
    function check(){
                var s = "<%=checker%>";
                if(s == "email"){
                    alert("Email ID already exits");
                }
                else if(s == "username"){
                    alert("Username already exists");
                }
    }
</script>

<body onload="check()">
         
<div id='cssmenu'>
<ul>
   <li class=''><a href='${pageContext.request.contextPath}/'><span>Home</span></a></li>
   <c:if test='${empty sessionScope.user}'>
   	<li><a href='${pageContext.request.contextPath}/login'><span>Login</span></a></li>
   	<li><a href='${pageContext.request.contextPath}/register'><span>Register</span></a></li>
   </c:if>
</ul>
</div>

<div id="wrapper" >
    
    <div id="errors"></div>
    <form name="login-form" class="login-form" action="checkRegister" id="form" method="post" onsubmit="return validate()">
	
		<div class="header">
		<h1>Register </h1>
		<span></span>
		</div>
	
		<div class="content">
                    <label for="email">Email ID: </label><br>
                    <input name="email" id="email" type="text" class="input username" placeholder="Email" style="width:80%;" /><br>
		<div class="user-icon"></div>
                <label for="username">Username:</label><br>
                <input name="username" id="username" type="text" class="input username" placeholder="Username" style="width:80%;" /><br>
		<div class="user-icon"></div>
                <label for="password">Password:</label><br>
                <input name="password" type="password" id="password" class="input password" placeholder="Password" style="width:80%;" /><br>
		<div class="user-icon"></div>
		<label for="confirm_password">Confirm Password: </label><br>
                <input name="confirm_password" type="password" id="confirm_password" class="input password" placeholder="Confirm Password"  style="width:80%;"/><br>
		<div class="user-icon"></div>
		<label for="fullName">Full Name: </label><br>
                <input name="fullName" type="text" id="fullName" class="input username" placeholder="Full Name(optional)" style="width:80%;" /><br>
		<div class="user-icon"></div>
		<label for="security_ques">Security Question: </label><br>
                <select name="security_ques" id="security_ques" class="input username" style="width:80%;">
                    <option value="What is your favorite movie?">What is your favorite movie?</option>
                    <option value="What is your mother's maiden name?">What is your mother's maiden name?</option>
                    <option value="What street did you grow up on?">What street did you grow up on?</option>
                    <option value="What was the make of your first car?">What was the make of your first car?</option>
                    <option value="What is the name of your first grade teacher?">What is the name of your first grade teacher?</option>
                  </select><br>
		<div class="user-icon"></div>
		<label for="answer">Answer: </label><br>
                <input name="answer" type="text" id="answer" class="input username" placeholder="Answer" style="width:80%;" />	<br>	
		</div>

		<div class="footer">		
                    <input type="submit" class="button" name="submit" value="Register" class="register" />
		</div>
	
	</form>
    

</div>
<div class="gradient"></div>


</body>
</html>
