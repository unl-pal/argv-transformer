<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>all school</title>
</head>
<body>
  <h1>Welcome to School Search</h1>
  <h3>School List </h3> 
  <ul>
     <c:forEach items="${schools}" var="school">
       <li>
         <c:out value="${school.name}"/>
       </li>
     </c:forEach>    
  </ul>
</body>
</html>