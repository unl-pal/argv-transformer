<%-- 
    Document   : login
    Created on : Apr 6, 2015, 7:27:50 PM
    Author     : rmsor_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="UserServlet">

            <h3>Please enter your name and password.</h3>

            <table>

                <tr>

                    <td>Name:</td>

                    <td><input type="text" value="${name}" name="name" required="true"/></td>

                </tr>

                <tr>

                    <td>Password:</td>

                    <td><input type="password" name="password" value="${password}"/></td>

                </tr>

            </table>

            <p>${msg}</p>

            <p><input type="submit" value="Login"/></p>

        </form>
    </body>
</html>
