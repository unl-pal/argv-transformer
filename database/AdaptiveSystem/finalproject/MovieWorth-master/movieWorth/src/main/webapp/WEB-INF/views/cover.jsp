<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Welcome to Worth</title>
	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- Global Stylesheet -->
	<link rel="stylesheet" href="${baseURL}/resources/css/cover.css">
	<style>
		body{
			background-image: url('${baseURL}/resources/images/cover_bg.jpg');
			background-size:cover;
			background-position: center;
		}
	</style>
</head>
<body>
    <div class="site-wrapper">
      <div class="site-wrapper-inner">
        <div class="cover-container">
          <div class="masthead clearfix">
            <div class="inner">
              <h3 class="masthead-brand">Worth</h3>
              <nav>
                <ul class="nav masthead-nav">
                  <li class="active"><a href="#">Cover</a></li>
                </ul>
              </nav>
            </div>
          </div>
          <div class="inner cover">
            <h1 class="cover-heading">Movies you may like.</h1>
            <p class="lead">Worth is a system where we try to recommend you great movies based on your potential interests.</p>
            <p class="lead">
              <a href="${baseURL}/index" class="btn btn-lg btn-default">Dive in</a>
            </p>
          </div>
          <div class="mastfoot">
            <div class="inner">
              <p>Team member: Fei H, Ran D, Lan Z, Yue Z</p>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>
