<%@ taglib prefix="sf"uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Add a School</title>
</head>
<body>
   <h1> Enter the school information</h1>
   <div>
     <sf:form method="get" modelAttribute="school">
       <fieldset>
         <table cellspacing="0">
            <tr>
               <th><label for="schoolName">Name:</label></th>
               <td><sf:input path="schoolName" size="15" id="school_name"/>
                  <small id="website_msg">Full name, please.</small></td>
            </tr>
               <td colspan="3"><input type="submit" /></td>
            </tr>  
          </table>
       </fieldset>
       
     </sf:form>
   </div>   
</body>
</html>