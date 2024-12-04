<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>

<c:choose>
	<c:when test="${sessionScope.user != null}">
	<img src="userPhoto?login=${user.login}" height="40">
		Hi, ${sessionScope.user.login} | <a href="<c:url value="logout"/>">Logout</a>
	</c:when>
	<c:otherwise>
		<a href="<c:url value="/login.jsp"/>">Login</a> | <a
			href="<c:url value="/register.jsp"/>">Sign Up</a>
	</c:otherwise>
</c:choose>
