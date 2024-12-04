<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
<head>
	<title>Edge register page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="outdoor" />
	<script type="application/x-javascript">
		 addEventListener("load", function() {setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
	</script>
	<meta charset utf="8">
	<!--fonts-->
	<link href='//fonts.googleapis.com/css?family=Fredoka+One'
		rel='stylesheet' type='text/css'>
	
	<!--fonts-->
	<!--bootstrap-->
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<!--coustom css-->
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<!--shop-kart-js-->
	<script src="js/simpleCart.min.js"></script>
	<!--default-js-->
	<script src="js/jquery-2.1.4.min.js"></script>
	<!--bootstrap-js-->
	<script src="js/bootstrap.min.js"></script>
	<!--form validator-->
	<script type="text/javascript"src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-registration-validator.js" charset="utf-8"></script>
	<!-- FlexSlider -->
	<script src="js/imagezoom.js"></script>
	<script defer src="js/jquery.flexslider.js"></script>
	<link rel="stylesheet" href="css/flexslider.css" type="text/css"
		media="screen" />
	<script>
		// Can also be used with $(document).ready()
		$(window).load(function() {
			$('.flexslider').flexslider({
				animation : "slide",
				controlNav : "thumbnails"
			});
		});
	</script>
	<!-- //FlexSlider-->
</head>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="head-bread">
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="index.jsp">Home</a></li>
				<li><a href="register.jsp">LOGIN</a></li>
				<li class="active">REGISTER</li>
			</ol>
		</div>
	</div>
	<!-- register_form -->
	<div class="reg-form">
		<div class="container">
			<div class="reg">
				<h3>Register Now</h3>
				<form name="register_form" action="registration" method="post" enctype="multipart/form-data">
					<ul>
						<li class="text-info">First Name:</li>
						<li><input name="firstName" id="firstName" type="text" value="${bean.firstName}" placeholder="Example: John"></li>
						<span id="firstNameSpan" />
					</ul>
					<ul>
						<li class="text-info">Last Name:</li>
						<li><input name="lastName" id="lastName" type="text" value="${bean.lastName}" placeholder="Example: Carter"></li>
					</ul>
					<ul>
						<li class="text-info">Login:</li>
						<li><input name="login" id="login" type="text" value="${bean.login}" placeholder="Example: Vano"></li>
					</ul>
					<ul>
						<li class="text-info">Email:</li>
						<li><input name="mail" id="mail" type="text" value="${bean.mail}" placeholder="Example: John_Carter@epam.com"></li>
					</ul>
					<ul>
						<li class="text-info">Password:</li>
						<li><input name="password"  id="password" type="password"
							placeholder="Example: John123"></li>
					</ul>
					<ul>
						<li class="text-info">Re-enter password:</li>
						<li><input name="re_password"  id="re_password" type="password" placeholder="Example: John123"></li>
					</ul>
					<ul>
						<li class="text-info">Choose your spam:</li>
						<li><input type="checkbox" name="notification" value="Windows 95/98 notification"> Windows 95/98</li>
					    <li><input type="checkbox" name="notification" value="Windows 2000 notification"> Windows 2000</li>
					    <li><input type="checkbox" name="notification" value="System X notification"> System X</li> 
					    <li><input type="checkbox" name="notification" value="Linux notification"> Linux</li>
					</ul>
					<ul>
						<li class="text-info">Add photo:</li>
						<li><input type="file" name="avatar"></li>
					</ul>
					<mct:captcha/>
					<input type="submit" class="submit" value="Register"> ${error}
					<p class="click"></p>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>