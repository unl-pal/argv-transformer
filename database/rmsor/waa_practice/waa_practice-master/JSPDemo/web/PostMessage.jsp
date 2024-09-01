<%-- 
    Document   : PostMessage
    Created on : Apr 2, 2015, 10:43:04 AM
    Author     : 984317
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title> Introduction to JSP demo – postback page </title>

    </head>

    <body>

        <p>This is the postback message</p>
        <%! int count = 0;%>

        The count is now:  

        <%= ++count%>



        <%-- This is a jsp scriptlet that increments the count --%>

        <!-- This is an html comment inserted by the increment comment -->

        <% count = count * 10;%>

        The count is now:  

        <%= count%>

    </body>

</html>
