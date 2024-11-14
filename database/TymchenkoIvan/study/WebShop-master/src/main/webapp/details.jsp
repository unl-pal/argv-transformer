<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
<head>
<title>Edge order details</title>
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

	<c:choose>
		<c:when
			test="${sessionScope.basket == null || basket.productsCount == 0 || sessionScope.user == null}">
			<c:redirect url="./basket.jsp" />
		</c:when>
		<c:otherwise>

			<div class="head-bread">
				<div class="container">
					<ol class="breadcrumb">
						<li><a href="index.jsp">Home</a></li>
						<li><a href="products">PRODUCTS</a></li>
						<li><a href="basket.jsp">BASKET</a></li>
						<li class="active">DETAILS</li>
					</ol>
				</div>
			</div>

			<!-- check-out -->
			<div class="check">
				<div class="container">
					<div class="col-md-9 cart-items">

						<table class="table table-striped">

							<c:forEach items="${basket.map}" var="entry">
								<tr>
									<td>${entry.key.type}&nbsp;/&nbsp;${entry.key.name}</td>
									<td>${entry.key.price}&nbsp;x&nbsp;${entry.value}</td>
									<td>Total : ${entry.key.price * entry.value}</td>
								</tr>
							</c:forEach>
							
								<tr>
									<td></td>
									<td>Total: <b>${basket.productsCount}</b></td>
									<td>Total: <b>${basket.totalPrice}</b></td>
								</tr>
								
						</table>

						<hr class="featurette-divider">

						<form name="details_form" class="form-horizontal" action="orderDetails" method="post">
							<fieldset>
								<!-- Select Basic -->
								<div class="form-group">
									<label class="col-md-4 control-label" for="delivery">Delivery:</label>
									<div class="col-md-4">
										<select name="delivery" class="form-control" id="delivery">
											<option value="address">Address</option>
											<option value="storage">From storage</option>
											<option value="mail">Mail</option>
										</select>
									</div>
								</div>

								<!-- Select Basic -->
								<div class="form-group">
									<label class="col-md-4 control-label" for="payment">Payment:</label>
									<div class="col-md-4">
										<select name="payment" class="form-control" id="payment">
											<option value="cash">Cash</option>
											<option value="card">Card</option>
										</select>
									</div>
								</div>

								<!-- Button -->
								<div class="form-group">
									<label class="col-md-4 control-label" for="button"></label>
									<div class="col-md-4">
										<button name="button" class="btn btn-success" id="button">Next step</button>
									</div>
								</div>

							</fieldset>
						</form>
						
						<hr class="featurette-divider">

					</div>
					<div class="clearfix"></div>
				</div>
			</div>

		</c:otherwise>
	</c:choose>

	<%@ include file="/jspf/footer.jspf"%>

</body>
</html>