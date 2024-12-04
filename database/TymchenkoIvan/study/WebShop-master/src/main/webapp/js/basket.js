$(document).ready(function() {
	addToCart(".item_add");
});

function addToCart(el) {
	$(el).click(function(event) {
		event.preventDefault();
		var itemId = $(this).attr("data-id");
		$.post("jsonservlet", {
			id : itemId,
			onlyCart : $(this).attr("data-only-cart"),
			action : $(this).attr("data-action")
		}).done(function(data) {
			if (data.error) {
				alert(data.error);
			} else {
				var basket = jQuery.parseJSON(data);
				$(".basketCount").text(basket.totalCount);
				$(".basketSum").text(basket.totalPrice);
				if(data.count<1 && $('#cartItems').length) {
					$('#cartItems').hide();
				}
				if (data.itemCount != 0) {
					$("#count" + itemId).text(data.itemCount);
					$("#sum" + itemId).text(data.itemSum);
				} else {
					$("#div" + itemId).hide();
				}
			}
		}).fail(function(error) {
			console.error(error);
		});

	})
};