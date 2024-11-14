<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Worth - Search</title>
<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<!-- Global Stylesheet -->
<link rel="stylesheet" href="${baseURL}/resources/css/global.css">
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
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
					<li class="active"><a href="${baseURL}/search">Search</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${baseURL}/profile">${pageContext.request.userPrincipal.name}</a></li>
					<li><a href="<c:url value='/j_spring_security_logout'/>">Sign Out</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="mainContent container">
		<div class="row searchbox">
			<div class="col-md-10"><input type="text" id="inputSearch" class="form-control" placeholder="Movie Title" autofocus></div>
			<div class="col-md-2"><button class="btn btn-default" id="sButton">Search</button></div>
		</div>
		<div class="row">
			<table class="table" id="slist"></table>
		</div>

	</div>
	<footer class="footer">
		<div class="container">
			<p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
		</div>
	</footer>
	<script>
		(function() {
			$("#sButton").click(function(){
				var keyword = $("#inputSearch").val().trim();
				if(keyword.length<1){
					return false;
				}else{
					getR(keyword);
				}
			});
			
			function getR(keyword){
				$.ajax({
					url : "${baseURL}/search/",
					data : JSON.stringify(keyword),
					type : "POST",
					dataType : 'json',
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
						xhr.setRequestHeader("Content-Type", "application/json");
					},
					success : function(data) {
						console.log(data);
						addSearchResult(data);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(jqXHR.status + " " + jqXHR.responseText);
					}
				});
			};

			function addSearchResult(data) {
				$('#slist').html("");
				for (var i = 0; i < data.length; i++) {
					var movie = data[i];
					$('#slist').append(
							"<tr><td><a href=\"${baseURL}/movie/"+movie.mid+"\">"
									+ movie.title + "</a></td></tr>");
				}
			}
		}());
	</script>
</body>
</html>
