<%-- 
    Document   : table
    Created on : Apr 3, 2015, 11:24:07 AM
    Author     : 984317
--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

         pageEncoding="ISO-8859-1"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<!DOCTYPE html>
<html>

    <table>

        <head>

            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

            <title>Lecture 5</title>

        </head>

        <body>

            <table>



                <c:forEach var="student" items="${students}">

                    <tr>

                        <td>${student.name}</td>

                        <td>${student.age}</td>

                    </tr>

                </c:forEach>



            </table>

        </body>

</html>
