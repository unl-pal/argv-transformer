<%@ taglib prefix="sf"uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Add a School</title>
<style>
.error {
color: #ff0000;
font-weight: bold;
}
</style>
</head>
<body>
   <h1> Enter the school information</h1>
   <div>
     <sf:form method="POST" modelAttribute="school">
        <form:errors path="*" cssClass="error" />
       <!-- <fieldset> -->
       <% int a = 0;
         
       %>
         <table cellspacing="0">
            <tr>
               <th><label for="name">Name:</label></th>
               <td><sf:input path="name" size="15" id="school_name"/>
                  <small id="website_msg">Full name, please.</small></td>
               <td><form:errors path="name" cssClass="error" /></td>
            </tr>
            <tr>
               <th><label for="website">Website:</label></th>
               <td><sf:input path="website" size="15" maxlength="30" id="website"/></td>
               <td><form:errors path="website" cssClass="error" /></td> 
            </tr>
            <tr>
              <td colspan="3"><input type="submit" /></td>
            </tr>  
          </table>
       <!-- </fieldset>-->
       
     </sf:form>
   </div>   
</body>
</html>