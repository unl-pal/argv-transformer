<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Worth - Homepage</title>
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
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand brand" href="${baseURL}/">Worth</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="${baseURL}/index">Home</a></li>
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
		<h2>Here are what we think you may like:</h2>
		<div class="row">
			<table class="table" id="mlist">
				<tr><th>Name</th><th>Genre</th></tr>
			</table>
		</div>
		<h2>Here are some popular movies you might be interested in:</h2>
		<div class="row">
			<table class="table" id="plist">
				<tr><th>Name</th><th>Genre</th></tr>
			</table>
		</div>
    </div>
   <footer class="footer">
      <div class="container">
        <p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
      </div>
    </footer>
<script>
(function(){
	$.ajax({
        url: "${baseURL}/recommend/slope",
        data: null,
        type: "GET",
        dataType : 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
        	console.log(data);
        	addMovies(data, "mlist");
        	getPopular(data);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + " " + jqXHR.responseText);
        }
    });


	function addMovies(data, table){
		for(var i = 0; i < data.length; i++){
			var movie = data[i],
				genre = "";
			for(var j = 0; j < movie.genre.length; j++){
				genre += '<span class="label label-info">'+movie.genre[j]+'</span>';
			}
			$('#'+table).append("<tr><td><a href=\"${baseURL}/movie/"+movie.mid+"\">"+movie.title+"</a></td><td>"+genre+"</td></tr>");
		}
	}
	
	function getPopular(slopeList){
		$.ajax({
	        url: "${baseURL}/recommend/popular",
	        data: null,
	        type: "GET",
	        dataType : 'json',
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accept", "application/json");
	            xhr.setRequestHeader("Content-Type", "application/json");
	        },
	        success: function(data) {
	        	var cleanList = [];
	        	//clean the list
	        	for(var i = 0; i < data.length; i++){
	        		for(var j = 0; j < slopeList.length; j++){
	        			if(slopeList[j].mid != data[i].mid){
	        				cleanList.push(data[i]);
	        				break;
	        			}
	        		}
	        	}
	        	console.log(cleanList);
	        	addMovies(cleanList, "plist");
	        },
	        error : function(jqXHR, textStatus, errorThrown) {
	            alert(jqXHR.status + " " + jqXHR.responseText);
	        }
	    });
	}
}());
</script>
</body>
</html>
