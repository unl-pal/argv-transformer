<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Worth - New User's Guide</title>
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
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="${baseURL}/profile">${pageContext.request.userPrincipal.name}</a></li>
			<li><a href="<c:url value='/j_spring_security_logout'/>">Sign Out</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="mainContent container">
		<h2>Please rate at least 5 movies from the list below:</h2>
		<div>
			<table class="table" id="mlist">
				<tr><th>Name</th><th>Genre</th><th></th></tr>
			</table>
		</div>
		<div class="row">
			<div class="col-md-10">
				<button id="loadMore" type="button" class="btn btn-default">Load More</button>
			</div>
			<div class="col-md-2 text-right">
				<button id="goodToGo" type="button" class="btn btn-default">That's it.</button>
			</div>
		</div>
    </div>
	<footer class="footer">
		<div class="container">
			<p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
			</div>
	</footer>
<script>
(function(){
	var times = 0,
		liked = [];
	
	$('#loadMore').click(function(){
		getBatch();
	});
	
	$('#goodToGo').click(function(){
		window.location.href = "${baseURL}/index";
	});
	
	function getBatch(){
		$.ajax({
	        url: "${baseURL}/recommend/start",
	        data: JSON.stringify(times),
	        type: "POST",
	        dataType : 'json',
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
	        success: function(data) {
	        	console.log(data);
	        	addMovies(data);
	        	times++;
	        },
	        error : function(jqXHR, textStatus, errorThrown) {
	            alert(jqXHR.status + " " + jqXHR.responseText);
	        }
	    });
	}
	
	function addMovies(data){
		for(var i = 0; i < data.length; i++){
			var movie = data[i],
				genre = "";
			for(var j = 0; j < movie.genre.length; j++){
				genre += '<span class="label label-info">'+movie.genre[j]+'</span>';
			}
			$('#mlist').append("<tr><td><a target=\"_blank\" href=\""+movie.imdbURL+"\">"+movie.title+"</a></td><td>"+genre+"</td><td><input id=\"m"+movie.mid+"\" type=\"hidden\" class=\"rating\" value=\"0\"/></td></tr>");
		}
		return bindRate(data);
	}
	
	function bindRate(data){
		for(var i = 0; i < data.length; i++){
			var movie = data[i];
			$('#m'+movie.mid).rating();
	    	$('#m'+movie.mid).on('change', function(){
	    		var rating = $(this).val(),
		    		mid = $(this).prop("id").slice(1);
	    		$.ajax({
	    		    url: "${baseURL}/rating/"+mid+"/"+rating,
	    	        type: "GET",
	    		});
			});
		}
	}
	getBatch();
}());
</script>
</body>
</html>
