<%-- 
    Document   : result
    Created on : Mar 31, 2015, 8:48:11 PM
    Author     : rmsor_000
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <h1>Beer Recommendation JSP</h1>
    <body>
        <% 
        List styles=(List) request.getAttribute("styles");
        Iterator it=styles.iterator();
        while(it.hasNext()){
            out.print("<br>try: "+it.next());
        }
        %>
    </body>
</html>
