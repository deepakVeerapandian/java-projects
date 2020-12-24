<%-- 
    Document   : products
    Created on : May 27, 2019, 12:50:45 AM
    Author     : deepa
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.*,java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Styles/products.css" type="text/css"/>
    </head>
    <body>
        User : ${sessionScope.UserName}  <a href="Membership?action=logout">Logout</a>
        <h1>Products</h1>
        <c:if test="${sessionScope.isValidUser}">
            <table>
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach items="${sessionScope.products}" var="prod">
                        <tr id="${prod.code}">
                            <td><c:out value="${prod.code}"/></td>
                            <td><c:out value="${prod.description}"/></td>
                            <td>$<c:out value="${prod.price}"/></td>
                            <td><a href="ProductManagement?action=displayProduct&code=${prod.code}">Edit</a></td>
                            <td><a href="ProductManagement?action=deleteProduct&code=${prod.code}">Delete</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form action="ProductManagement?action=addProduct" method="post">
                <input class="addProduct" type="submit" value="Add Product" name="addProduct" />
            </form>
            
                
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
