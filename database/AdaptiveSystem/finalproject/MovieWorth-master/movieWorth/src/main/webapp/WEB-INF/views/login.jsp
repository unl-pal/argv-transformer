<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Worth - Sign In</title>
	<script><c:if test="${not empty goback}">${goback}</c:if></script>
	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- Global Stylesheet -->
	<link rel="stylesheet" href="${baseURL}/resources/css/global.css">
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
          </button>
          <a class="navbar-brand brand" href="${baseURL}/">Worth</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          </ul>
          <ul class="nav navbar-nav navbar-right">
			<li class="active"><a href="#">Sign In</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="mainContent container">
	<div class="row">
		<div class="col-md-4">
			<form class="form-signin" action="<c:url value='/j_spring_security_check'/>" method="POST">
		        <h2 class="form-signin-heading">Please sign in</h2>
		        <c:if test="${not empty error}">
					<div class="alert alert-warning" role="alert">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="alert alert-info" role="alert">${msg}</div>
				</c:if>
		        <label for="inputUsername" class="sr-only">Username</label>
		        <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Username" required autofocus>
		        <label for="inputPassword" class="sr-only">Password</label>
		        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
		        <br>
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  		    </form>
		</div>
		<div class="col-md-8">
			<h2>Not a member?</h2>
			<p>Just <a href="${baseURL}/reg">register</a> now and get a movie worth watching tonight.</p>
		</div>
	</div>
    </div>
    
	<footer class="footer">
      <div class="container">
        <p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
      </div>
    </footer>
</body>
</html>
