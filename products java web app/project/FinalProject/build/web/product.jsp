<%-- 
    Document   : product
    Created on : May 27, 2019, 12:51:57 AM
    Author     : deepak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Styles/product.css" type="text/css"/>
        
    </head>
    <body>
        User : ${sessionScope.UserName}  <a href="Membership?action=logout">Logout</a>
        <h1>Product</h1>
        <c:if test="${sessionScope.isValidUser}">
            <form action="ProductManagement?action=saveProduct" method="post" type="redirect">
                <label>Code:</label>
                <input type="text" name="code" value="${isEdited?code:''}" required/><br>
                <label>Description:</label>
                <textarea type="text" name="description" value="" required>${isEdited? description :''}</textarea><br>
                <label>Price:</label>
                <input type="number" name="price" value="${isEdited? price :''}" max="99999" step="0.01" required/><br>

                <input id="Update_Product" type="submit" value="Update Product" name="UpdateProduct" />
                <form>
                    <input id="ViewProducts" type="button" value="View Products" onClick="JavaScript:window.location='ProductManagement?action=displayProducts';" name="ViewProducts" />
                </form>
                
            </form>
        </c:if>
        <c:if test="${sessionScope.isValidUser == null || sesessionScope.isValidUser == false}">
            <h1>Not Authorized!</h1>
            <% 
             String site = new String("Membership?action=login");
             response.setStatus(response.SC_MOVED_TEMPORARILY);
             response.setHeader("Location", site);
            %>
        </c:if>
    </body>
</html>

