function checkForm(form, ccId) {
   var name = form[ccId + ':form:name'].value;
   var pwd = form[ccId + ':form:password'].value;

   if (name == "" || pwd == "") {
      alert("Please enter name and password.");
      return false;
   }
   return true;
}