$(document).ready(function(){

$('.submit').click(function(e){
	
    validateForm(e);   
});

function validateForm(e){

    var emailRegExp=/^\w+[\w-.]*@\w+((-\w+)|(\w*))\.[a-z]{2,3}$/;
	var nameRegExp=/^[A-Z\u0410-\u042f][a-z\u0430-\u044f]{2,20}$/;
	var passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;
	var captchaRegExp = /^\d+$/;

    var firstName = $('#firstName').val();
    var lastName = $('#lastName').val();
    var login = $('#login').val();
    var mail = $('#mail').val();
	var pass = $('#password').val();
	var re_pass = $('#re_password').val();
	var captchaCode = $('#captchaCode').val();

    var inputVal = new Array(firstName, lastName, login, mail, pass, re_pass, captchaCode);

		$('.error').hide();
		
        if(!nameRegExp.test(inputVal[0])){
            $('#firstName').after('<span class="error"> First Name cant start with lower case letter and contain numbers. </span>');
			e.preventDefault();
        } 
		
		if(!nameRegExp.test(inputVal[1])){
            $('#lastName').after('<span class="error"> Last Name cant start with lower case letter and contain numbers.</span>');
			e.preventDefault();
        }
		
		if(!nameRegExp.test(inputVal[2])){
            $('#login').after('<span class="error"> Login cant start with lower case letter and contain numbers.</span>');
			e.preventDefault();
        } 
		
		if(!emailRegExp.test(inputVal[3])) {
            $('#mail').after('<span class="error"> Wrong email.</span>');
			e.preventDefault();
		}
		
		if(!passwordRegExp.test(inputVal[4])) {
            $('#password').after('<span class="error"> Wrong password. It must contain minimum 8 characters and at least 1 number.</span>');
			e.preventDefault();
		}

		if(inputVal[4]!=inputVal[5]) {
            $('#re_password').after('<span class="error"> You havent confirm your password.</span>');
			e.preventDefault();
		} 

		if(!captchaRegExp.test(inputVal[6])) {
            $('#captchaCode').after('<span class="error"> You need to enter capcha.</span>');
			e.preventDefault();
		}
}   

});