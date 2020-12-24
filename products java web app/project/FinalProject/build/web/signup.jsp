<%-- 
    Document   : signup
    Created on : May 27, 2019, 1:08:00 AM
    Author     : rishi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Styles/main.css" type="text/css" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Sign-up Form</font></h1>
        <form action="Membership?action=signup" method="post">
            <label>First Name:</label>
            <input type="text" name="FirstName" value="" required/><br>
            <label>Last Name:</label>
            <input type="text" name="LastName" value="" required/><br>
            <label>Email:</label>
            <input type="email" name="Email" value="" required/><br>
            <label>UserName:</label>
            <input type="text" name="UserName" value="" required/><br>
            <label>Password:</label>
            <input type="password" name="Password" value="" minlength="8" required/><br>
        
            <input id="submitButton" type="submit" value="Sign up" />
        </form>
        
           
    </body>
</html>
