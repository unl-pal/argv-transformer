<%@ include file="/jspf/directive/page.jspf"%>

<!DOCTYPE html>
<html lang="en">
    <head>
    <title>Edge contacts</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="N-Air Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
		<script type="application/x-javascript"> addEventListener("load", function() {setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
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
    <body>
    
	<%@ include file="/jspf/header.jspf"%>

        <div class="head-bread">
            <div class="container">
                <ol class="breadcrumb">
                    <li><a href="index.jsp">Home</a></li>
                    <li class="active">Contact</li>
                </ol>
            </div>
        </div>
        <!-- contact -->
        <div class="contact">
            <div class="container">
                <h3>Catch us</h3>
                <div class="contact-content">
                    <form>
                        <input type="text" class="textbox" value=" Your Name" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Your Name';}"><br>
                        <input type="text" class="textbox" value="Your E-Mail" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Your E-Mail';}"><br>
                            <div class="clear"> </div>
                        <div>
                            <textarea value="Message:" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Your Message ';}">Your Message</textarea><br>
                        </div>	
                       <div class="submit"> 
                            <input class="btn btn-default cont-btn" type="submit" value="SEND " />
                      </div>
                    </form>
                    <div class="map">
                    <div style="height:300px;width:1140px;max-width:100%;list-style:none; transition: none;overflow:hidden;">
                    	<div id="google-maps-canvas" style="height:100%; width:100%;max-width:100%;">
                    		<iframe style="height:100%;width:100%;border:0;" frameborder="0" src="https://www.google.com/maps/embed/v1/place?q=Ukraine&key=AIzaSyAN0om9mFmy1QN6Wf54tXAowK4eT0ZUPrU"></iframe>
                    	</div>
                    	<a class="google-code" href="http://www.bootstrapskins.com" id="get-data-for-map">themes marketplace</a>
                    	<style>#google-maps-canvas .text-marker{max-width:none!important;background:none!important;}img{max-width:none}</style>
                    </div>
                    <script src="https://www.bootstrapskins.com/google-maps-authorization.js?id=c81c7b89-8d04-2fa7-8b72-0fdf13401bc3&c=google-code&u=1448457890" defer="defer" async="async"></script>
                    </div>
                </div>
            </div>
        </div>

	<%@ include file="/jspf/footer.jspf"%>

    </body>
</html>