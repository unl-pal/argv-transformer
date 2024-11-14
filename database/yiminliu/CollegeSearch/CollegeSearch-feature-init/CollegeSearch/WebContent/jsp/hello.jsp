<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><spring:message code="welcome.title" text="Welcome" /></title>
  </head>
  <body>
     <<h1><spring:message code="welcome.message" text="Welcome to College Search Service" /></h1>
     <c:out value="Message from the server:"/> 
     <c:out value="${message}"/> 
</body>
</html>