<%@ include file="/jspf/directive/page.jspf"%>

<html lang="en">
    <head>
        <title>Edge products page</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="outdoor" />
		<script type="application/x-javascript"> addEventListener("load", function() {setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<meta charset utf="8">
		<!--fonts-->
		<link href='//fonts.googleapis.com/css?family=Fredoka+One' rel='stylesheet' type='text/css'>
		
		<!--fonts-->
        <!--form-css-->
        <link href="css/form.css" rel="stylesheet" type="text/css" media="all" />
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
                    <li class="active">PRODUCTS</li>
                </ol>
            </div>
        </div>
        <div class="products-gallery">
           <div class="container">
			
               <div class="col-md-9 grid-gallery">
               	<c:choose>
               	
               		<c:when test="${error != null}">
						<h4>${error}</h4>
					</c:when>
					
	           		<c:otherwise>
	           			<c:if test="${fn:length(products) == 0}">
	           				<h4>Sorry, no items found. Try again.</h4>
	           			</c:if>
					</c:otherwise>
					
				</c:choose>
        
               <c:forEach var="item" items="${products}">
               		<div class="col-md-4 grid-stn simpleCart_shelfItem">
               		<!-- normal -->
               		
               			<div class="ih-item square effect3 bottom_to_top">
                        	<div class="bottom-2-top">
                        		<div class="img">
                        			<img src="images/products/${item.id}.jpg" alt="Product photo" class="img-responsive gri-wid">
                        		</div>
                                <div class="info">
                                    <div class="pull-left styl-hdn">
                                        <h3>"${item.name}"</h3>
                                    </div>
                                    <div class="pull-right styl-price">
                                        <p><a  href="#" class="item_add">
                                        <span class="glyphicon glyphicon-shopping-cart grid-cart" aria-hidden="true"></span> 
                                        <span class=" item_price">"${item.price}"</span></a></p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                        	</div>
                    	</div>
                    	
                    	<!-- end normal -->
                        <div class="quick-view">
                            <a href="product?id=${item.id}">Quick view</a>
                            
                            <hr class="featurette-divider">
                            
                            <a class="item_add" data-id="${item.id}" data-action="add" href="">&nbsp;To basket</a> 
                        </div>
                        
               		</div>
				</c:forEach>

				<div align="center">
		            <ul class="pagination">
		                <c:forEach begin="1" end="${pages}" var="i">
		                    <c:choose>
		                        <c:when test="${page == i}">
		                            <li class="active"><a href="${url}">${i}</a></li>
		                        </c:when>
		                        <c:otherwise>
		                            <li><a href="${ util:changePage(url, i) }">${i}</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                </c:forEach>
		            </ul>
		        </div>
                    
            <div class="clearfix"></div>
                </div>
            <form name="sort_form" action="products" method="get">
                <div class="col-md-3 grid-details">
                    <div class="grid-addon">
                    
                <section class="sky-form">
					<h4><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Search by name</h4>
					<input name="search" id="search" value="${sortBean.search}" type="text" maxlength="20">
				</section> 	
				
				<section class="sky-form">
					<h4><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Price</h4>
					<h5>Min:</h5><input name="min_price" id="min_price" value="${sortBean.minPrice}" type="number" min="0" max="100000">
                    <h5>Max:</h5><input name="max_price" id="max_price" value="${sortBean.maxPrice}" type="number" min="0" max="100000">
                    
                    <h5>Sort by:</h5>
                    <select name="sort">
                    	<option value="default">Not sorted</option>
					    <option value="min-max"<c:if test="${sortBean.sorting == 'min-max'}">selected</c:if> >Min - Max</option>
					    <option value="max-min"<c:if test="${sortBean.sorting == 'max-min'}">selected</c:if> >Max - Min</option>
					</select>
				</section> 	
				
				<section class="sky-form">
					<h4><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Type</h4>
					<label class="checkbox"><input type="checkbox" name="type" value="backpack" 
				       <c:if test="${ util:contains(sortBean.types, 'backpack') }">checked</c:if> ><i></i>Backpacks</label>
					<label class="checkbox"><input type="checkbox" name="type" value="tent" 
				       <c:if test="${ util:contains(sortBean.types, 'tent') }">checked</c:if> ><i></i>Tents</label>
					<label class="checkbox"><input type="checkbox" name="type" value="sleepeng bag" 
				       <c:if test="${ util:contains(sortBean.types, 'sleepeng bag') }">checked</c:if> ><i></i>Sleeping bag</label>
				</section>
				
				<section class="sky-form">
					<h4><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Brand</h4>
					<label class="checkbox"><input type="checkbox" name="brand" value="deuter" 
				       <c:if test="${ util:contains(sortBean.brands, 'deuter') }">checked</c:if> ><i></i>Deuter</label>
					<label class="checkbox"><input type="checkbox" name="brand" value="tatonka" 
				       <c:if test="${ util:contains(sortBean.brands, 'tatonka') }">checked</c:if> ><i></i>Tatonka</label>
					<label class="checkbox"><input type="checkbox" name="brand" value="millet" 
				       <c:if test="${ util:contains(sortBean.brands, 'millet') }">checked</c:if> ><i></i>Millet</label>
				</section>
				
				<section class="sky-form">
					<h4><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>Show on page</h4>
					<label class="checkbox"><input type="radio" name="on_page" value="6" 
						<c:if test="${sortBean.onPage == '6'}">checked</c:if> 
						><i></i>6 products</label>
					<label class="checkbox"><input type="radio" name="on_page" value="12"
						<c:if test="${sortBean.onPage == '12'}">checked</c:if> 
					><i></i>12 products</label>
				</section>
				
				<section class="sky-form">
                    <input type="submit" class="btn btn-default log-bar" role="button" value="Sort">
				</section>	
				
                    </div>
               </div>
            </form>
            
            <div class="clearfix"></div>
            </div> 
        </div>
   
		<%@ include file="/jspf/footer.jspf"%>
		
    </body>
</html>