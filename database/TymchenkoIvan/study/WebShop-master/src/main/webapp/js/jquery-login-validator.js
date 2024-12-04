$(document).ready(function(){

$('.submit').click(function(e){
	
    validateForm(e);   
});

function validateForm(e){

	var nameRegExp=/^[A-Z\u0410-\u042f][a-z\u0430-\u044f]{2,20}$/;
	var passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;

    var login = $('#login').val();
	var pass = $('#password').val();

    var inputVal = new Array(login, pass);

		$('.error').hide();
		
		if(!nameRegExp.test(inputVal[0])){
            $('#login').after('<span class="error"> Login cant start with lower case letter and contain numbers.</span>');
			e.preventDefault();
		}
		
		if(!passwordRegExp.test(inputVal[1])) {
            $('#password').after('<span class="error"> Wrong password. It must contain minimum 8 characters and at least 1 number.</span>');
			e.preventDefault();
		}
}   

});