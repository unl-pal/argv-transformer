<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Worth - Register</title>
	<script><c:if test="${not empty goback}">${goback}</c:if></script>
	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- Validator -->
	<script src="${baseURL}/resources/js/validator.min.js"></script>
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
			<li><a href="${baseURL}/login">Login</a></li>
			<li class="active"><a href="#">Register</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="mainContent container">
    	<h2>Register</h2>
		<div class="row">
			<div class="col-md-4">
				<form id="regform" role="form" data-toggle="validator">
					<!-- Username -->
					<div class="form-group">
				    	<label for="username" class="control-label">Username</label>
				    	<input name="login" id="username" pattern="^([_A-z0-9]){3,}$" maxlength="15" type="text" class="form-control" placeholder="your preferred username" required>
				    	<div class="help-block with-errors">No less than 3, Up to 15 letters, numbers and underscores.</div>
					</div>
					<!-- Password and Repeat -->
					<div class="form-group">
						<label for="password" class="control-label">Password</label>
						<input name="pass" id="password" data-minlength="8" type="password" class="form-control" placeholder="password" required>
						<span class="help-block">Minimum of 8 characters</span>
					</div>
					<div class="form-group">
						<label for="passwordagain" class="control-label">Password Confirm</label>
						<input id="passwordagain" data-match="#password" data-match-error="These don't match" type="password" class="form-control" placeholder="password Confirm" required>
						<div class="help-block with-errors"></div>
					</div>
					<!-- Gender -->
					<div class="form-group">
				    	<label class="control-label">Gender</label>
				    	<div class="row">
				    		<div class="col-md-6"><input type="radio" name="gender" id="genderm" value="M" checked/>Male</div>
				    		<div class="col-md-6"><input type="radio" name="gender" id="genderf" value="F"/>Female</div>
				    	</div>
					</div>
					<div class="row">
					<div class="col-md-4">
						<!-- Age -->
						<div class="form-group">
					    	<label for="age" class="control-label">Age</label>
					    	<input name="age" id="age" pattern="^([0-9]){1,2}" maxlength="2" type="text" class="form-control" placeholder="18" required>
					    	<div class="help-block with-errors">Must be numbers</div>
						</div>
					</div>
					<div class="col-md-8">
						<!-- Zipcode -->
						<div class="form-group">
					    	<label for="zipcode" class="control-label">Zipcode</label>
					    	<input name="zipcode" id="zipcode" pattern="^([0-9]){5,5}" maxlength="5" type="text" class="form-control" required>
					    	<div class="help-block with-errors">Must be numbers exactly 5 digits</div>
						</div>
					</div>
					</div>
					<!-- Occupation -->
					<div class="form-group">
				    	<label for="occupation" class="control-label">Occupation</label>
				    	<select class="form-control" name="occupation" id="occupation">
				    		<c:forEach var="occupation" items="${occupations}">
				   	    	<option value="${occupation}">${occupation}</option>
				   	    	</c:forEach>
				    	</select>
					</div>
					<button id="regdo" type="submit" class="btn btn-default">Register</button>
				</form>
			</div>
	    </div>
    </div>
	<footer class="footer">
      <div class="container">
        <p class="text-muted">Team member: Fei H, Ran D, Lan Z, Yue Z</p>
      </div>
    </footer>
<script>
$('#regform').validator().on('submit', function (e){
	if(e.isDefaultPrevented()){
		return false;
	}else{
	    e.preventDefault();
	    var json = {
	    		"username" : $('#username').val(),
	    		"password" :$('#password').val(),
	    		"gender" : (function(){
	    			var gender = null;
	    			if($('#genderf').prop('checked')){
	    				gender = 'F';
	    			}else if($('#genderm').prop('checked')){
	    				gender = 'M';
	    			}
	    			return gender;
	    		}()),
	    		"age" : $('#age').val(),
	    		"zipcode" : $('#zipcode').val(),
	    		"occupation" : $('#occupation').val()
	    	};
	    regPost(json);
	}
});

function regPost(jsonObj){
	var req = JSON.stringify(jsonObj);
	$.ajax({
        url: "${baseURL}/reg",
        data: req,
        type: "POST",
        dataType : 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
        	if(data.status)
        		window.location.href = "${baseURL}/login";
        	else
        		alert("Please check your information again.");
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + " " + jqXHR.responseText);
        }
    });
}
</script>
</body>
</html>
