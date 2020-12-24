<%-- 
    Document   : login
    Created on : May 29, 2019, 8:51:56 PM
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
        <h1><font color="red"> Log-in</font></h1> 
        <form action="Membership?action=login" method="post" >                  
            <label>Username</label>
            <input type="text" name="Username" value="" required/><br>
            <label>Password</label>
            <input type="password" name="Password" value="" minlength="8" required/><br>
            <input id="submitButton" type="submit" value="Login" name="Login"/><br>
        </form>
            <a href="Membership?action=signup">New User? Register here</a>       
    </body>
</html>
