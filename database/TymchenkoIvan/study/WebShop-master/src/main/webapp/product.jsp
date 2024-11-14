<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
    <head>
    <title>Edge product page</title>
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
			<link href="css/style.css" rel="stylesheet" type="text/css"/>
        <!--shop-kart-js-->
        <script src="js/simpleCart.min.js"></script>
		<!--default-js-->
			<script src="js/jquery-2.1.4.min.js"></script>
		<!--bootstrap-js-->
			<script src="js/bootstrap.min.js"></script>
		<!--script-->
		<script type="text/javascript" src="js/basket.js" charset="utf-8"></script>
         <!-- FlexSlider -->
            <script src="js/imagezoom.js"></script>
              <script defer src="js/jquery.flexslider.js"></script>
            <link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" />

            <script>
            // Can also be used with $(document).ready()
            $(window).load(function() {
              $('.flexslider').flexslider({
                animation: "slide",
                controlNav: "thumbnails"
              });
            });
            </script>
        <!-- //FlexSlider-->
    </head>
        
    <%@ include file="/jspf/header.jspf"%>
    
        <div class="head-bread">
            <div class="container">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="products">PRODUCTS</a></li>
                    <li class="active">${product.name}</li>
                </ol>
            </div>
        </div>
        
        <div class="showcase-grid">
            <div class="container">
                <div class="col-md-8 showcase">
                    <div class="flexslider">
                          <ul class="slides">
                            <li data-thumb="images/products/${product.id}.jpg">
                                <div class="thumb-image"> <img src="images/products/${product.id}.jpg" data-imagezoom="true" class="img-responsive"> </div>
                            </li>
                          </ul>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <div class="col-md-4 showcase">
                    <div class="showcase-rt-top">
                        <div class="pull-left shoe-name">
                            <h3>"${product.name}"</h3>
                            <p>"${product.type}"</p>
                            <p>"${product.brand}"</p>
                            <h4>&#36;"${product.price}"</h4>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    
                    <hr class="featurette-divider">
                    
                    <li class="ad-2-crt simpleCart_shelfItem">
                    	<a class="btn item_add" class="item_add" href="" data-id="${product.id}" data-action="add"  role="button">ADD TO BASKET</a>
                    </li>
                </div>
        <div class="clearfix"></div>
            </div>
        </div>
        
        <div class="specifications">
            <div class="container">
              <h3>Description</h3> 
                <div class="detai-tabs">
                    <!-- Tab panes -->
                    <div class="tab-content">
	                    <div role="tabpanel" class="tab-pane active" id="home">
	                    "${product.text}"
	                    </div>
                    </div>
                </div>
            </div>
        </div>
        
		<%@ include file="/jspf/footer.jspf"%>
		
    </body>
</html>
    