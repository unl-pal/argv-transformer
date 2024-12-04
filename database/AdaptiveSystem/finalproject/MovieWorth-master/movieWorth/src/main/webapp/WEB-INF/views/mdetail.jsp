<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Worth - ${mName}</title>
	<meta charset="UTF-8"/>
	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- Global Stylesheet -->
	<link rel="stylesheet" href="${baseURL}/resources/css/global.css">
	<!-- Star Rating -->
	<link href="${baseURL}/resources/css/font-awesome.css" rel="stylesheet">
	<script type="text/javascript" src="${baseURL}/resources/js/tooltip.js"></script>
    <script type="text/javascript" src="${baseURL}/resources/js/bootstrap-rating.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand brand" href="${baseURL}/">Worth</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="${baseURL}/index">Home</a></li>
            <li><a href="${baseURL}/search">Search</a></li>
          	<li><a href="#">${mName}</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="${baseURL}/profile">${pageContext.request.userPrincipal.name}</a></li>
			<li><a href="<c:url value='/j_spring_security_logout'/>">Sign Out</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="container">
    	<h1><a target="_blank" href="${mURL}">${mName}</a></h1>
		<p>Release Date:${mDate}</p>
		<p>
		<c:forEach var="genre" items="${mGenre}">
		<span class="label label-info">${genre}</span>
		</c:forEach>
		</p>
		<p>Total Rating:<c:forEach begin="1" end="${mRatingInt}" varStatus="loop"><i class='glyphicon glyphicon-star'></i></c:forEach>(${mRating})</p>
		<p>Your Rating:<input type="hidden" class="rating" value="${myRating}"/></p>
    	<script>
    		$('.rating').on('change', function(){
    			var rating = $(this).val();
    			$.ajax({
    		        url: "${baseURL}/rating/${mId}/"+rating,
    		        type: "GET",
    		    });
			});
    	</script>
    </div>
    <footer class="footer">
      <div class="container">
        <p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
      </div>
    </footer>
</body>
</html>
