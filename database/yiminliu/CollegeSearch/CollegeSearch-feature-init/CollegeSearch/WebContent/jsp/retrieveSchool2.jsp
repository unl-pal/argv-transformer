<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="retrieve school.heading"/></h1>
<c:out value="School List"/>
  <table border="1">
    <tr>
       <th>School Name</th>
       <th>Website</th>
    </tr>
    <tr>
        <td>${school.name}</td>
        <td>${school.website}</td>
    </tr>
  </table>
  <p></p>
   Handling time : ${handlingTime} ms
</body>
</html>
