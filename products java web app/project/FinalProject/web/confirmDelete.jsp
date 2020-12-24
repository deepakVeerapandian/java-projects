  <%-- 
    Document   : confirmDelete
    Created on : May 29, 2019, 10:57:10 PM
    Author     : rishi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Styles/main.css" type="text/css" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.isValidUser}">
            <h1><font color="red">Are you sure you want to delete this product?</font></h1>
            <label>Code:</label>  ${code}<br>
            <label>Description:</label>  ${description}<br>
            <label>Price:</label> $ ${price}<br>
            <input type="button" onclick="JavaScript:window.location='ProductManagement?action=delete&code=${code}';" value="Yes" name="Yes" />
            <input type="button" onClick="JavaScript:window.location='products.jsp';"value="No" name="No" />            
        </c:if>
        <c:if test="${sessionScope.isValidUser == null || sessionScope.isValidUser == false}">
            <h1>Not Authorized!</h1>
            <% 
             String site = new String("Membership?action=login");
             response.setStatus(response.SC_MOVED_TEMPORARILY);
             response.setHeader("Location", site);
            %>
        </c:if>        
    </body>
</html>
