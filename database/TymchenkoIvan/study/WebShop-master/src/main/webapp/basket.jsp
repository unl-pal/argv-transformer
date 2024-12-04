<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
	<head>
		<title>Edge basket</title>
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

	<div class="head-bread">
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="index.jsp">Home</a></li>
				<li><a href="products">PRODUCTS</a></li>
				<li class="active">BASKET</li>
			</ol>
		</div>
	</div>

	<!-- check-out -->
	<div class="check">
		<div class="container">
			<div class="col-md-9 cart-items">

				<c:choose>
					<c:when
						test="${sessionScope.basket == null || basket.productsCount == 0}">
						<h4>Empty basket</h4>
					</c:when>
					<c:otherwise>

						<c:forEach items="${basket.map}" var="entry">

							<div class="cart-header">
								<div class="cart-sec simpleCart_shelfItem">
									<div class="cart-item cyc">
										<img src="images/products/${entry.key.id}.jpg"
											class="img-responsive" alt="" />
									</div>
									<div class="cart-item-info">
										<ul class="qty">
											<li><p>${entry.key.price}x ${entry.value}</p></li>
											<li><p>Total : ${entry.key.price * entry.value}</p></li>
											<li><p>
													<a href=""
														onclick="postToUrl('basketAdd', {'id':'${entry.key.id}'}, 'POST');">PLUS</a>
													&nbsp;/&nbsp; <a href=""
														onclick="postToUrl('basketMinus', {'id':'${entry.key.id}'}, 'POST');">MINUS</a>
												</p></li>
											<li><p>
													<a href=""
														onclick="postToUrl('basketDelete', {'id':'${entry.key.id}'}, 'POST');">DELETE</a>
												</p></li>
										</ul>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>

						</c:forEach>

						<hr class="featurette-divider">

						<ul class="total_price">
							<li></li>
							<li></li>
							<li class="last_price"><h4>Total
									price:&nbsp;&nbsp;${basket.totalPrice}</h4></li>
							<div class="clearfix"></div>
							<c:choose>
								<c:when test="${sessionScope.user != null}">
									<li><a class="order" href="./details.jsp">Next step</a></li>
								</c:when>
								<c:otherwise>
									<li><a class="order" href="./login.jsp">Please, login
											before you buy</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</c:otherwise>
				</c:choose>


			</div>
			<div class="clearfix"></div>
		</div>
	</div>

	<%@ include file="/jspf/footer.jspf"%>

</body>
</html>