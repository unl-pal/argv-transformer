<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <h1>Welcome to the School Search Page</h1>
  
  <h3>get All Schools</h3>
    <form action="listAllSchools" method="get">
       <table>
         <tr>
            <input type="submit" value="Go"/>
          </tr>   
       </table>
    </form> 
  <h3>get Schools by rank</h3>
    <form action="listTopSchools" method="post">
       <table>
          <tr>
            Rank:
             <input type="text" name="rank"/>
          </tr>
          <tr>   
             <input type="submit" value="Go"/>
          </tr>   
       </table>
    </form> 
    <form action="getSchoolDetail" method="post">
       <h3>get School detail by name</h3>
       Court Name
       <input type="text" name="schoolName" value="${schoolName}" />
       <input type="submit" value="Query" />
    </form>
</body>
</html>