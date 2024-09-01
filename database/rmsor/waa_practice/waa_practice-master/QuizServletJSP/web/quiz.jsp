<%-- 
    Document   : quiz
    Created on : Apr 2, 2015, 8:29:19 PM
    Author     : rmsor_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>NumberQuiz</title>
    </head>
    <body>
        <form method='post'>
            <h3>Have fun with NumberQuiz!</h3>
            <p>Your current score is: ${currentScore}<br/><br/>
            <p>Guess the next number in the sequence!  [ ${currentQues} , <span style='color:red;font-weight:bold'>?</span> ] </p>

            <p>Your answer:<input type='text' name='txtAnswer' value=''/></p> 
           <% 
               Boolean errorValidation = (Boolean) request.getAttribute("errorValidation");
            if (errorValidation) {  //REFACTOR?-- assumes answer null only when first open page
            %>
            <p style='color:red'>Input must be integer between 4 and 100! Please try again</p> 
            <%}%>
            <%/* if incorrect, then print out error message */

                String answer = (String) request.getAttribute("answer");
                Boolean error = (Boolean) request.getAttribute("error");
                if (error && answer != null) {  //REFACTOR?-- assumes answer null only when first open page
            %>
            <p style='color:red'>Your last answer was not correct! Please try again</p> 
            <%}%>
            <p><input type='submit' name='btnNext' value='Next' /></p> 
        </form>
    </body>
</html>
