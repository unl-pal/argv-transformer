<%-- 
    Document   : choose
    Created on : Apr 2, 2015, 11:05:19 AM
    Author     : 984317
--%>

<%@page contentType="text/html" import="java.io.*,java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

  <head>

    <title>Choose</title>

  </head>

  <body>

    <form method='post' action='ChooseServlet'>

      <p>Is JSP cool?</p>
      <input type='radio' value='1' name='radioJSPCool' ${yesCheckKey} ><span>Yes</span><br/>

      <input type='radio' value='0' name='radioJSPCool' ${noCheckKey}><span>No</span><br/>

      <input type='submit' name='btnSubmit' value='Submit'/>

      <p>Is JSF way cool?</p>

      <input type='radio' value='1' name='JSFwayCool' ${rsObjCool.yesCheck}><span>Yes</span><br/>

      <input type='radio' value='0' name='JSFwayCool' ${rsObjCool.noCheck}><span>No</span><br/>

      <input type='submit' name='btnSubmit' value='Submit'/>

      <p>Is the moon made of cheese?</p>

      <input type='radio' value='1' name='moonCheese'><span>Yes</span><br/>

      <input type='radio' value='0' name='moonCheese'><span>No</span><br/>

      <input type='submit' name='btnSubmit' value='Submit'/>

 

    </form>

  </body>

</html>