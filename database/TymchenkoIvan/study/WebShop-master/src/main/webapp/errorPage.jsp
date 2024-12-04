<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
<head>
	<title>Edge error page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="outdoor" />
	<script type="application/x-javascript">	
	addEventListener("load", function() {setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
	</script>
	<meta charset utf="8">
	<!--fonts-->
	<link href='//fonts.googleapis.com/css?family=Fredoka+One' rel='stylesheet' type='text/css'>
	
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
	<script type="text/javascript"
		src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-login-validator.js"
		charset="utf-8"></script>
	<!-- FlexSlider -->
	<script src="js/imagezoom.js"></script>
	<script defer src="js/jquery.flexslider.js"></script>
	<link rel="stylesheet" href="css/flexslider.css" type="text/css"
		media="screen" />
	<script type="text/javascript" src="js/post-to-url.js" charset="utf-8"></script>
	
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

	<!-- check-out -->
	<div class="check">
		<div class="container">
			<div class="col-md-9 cart-items">

				<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}" />
				<c:set var="message" value="${requestScope['javax.servlet.error.message']}" />
				
				<h1>${code}</h1>
				<h4>${message}</h4>

			</div>
			<div class="clearfix"></div>
		</div>
	</div>

</body>
</html>