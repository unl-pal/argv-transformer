if (!com) var com = {}
if (!com.corejsf) {
   com.corejsf = {
	   showProgress: function(data) {
   	   var inputId = data.source.id
  	      var progressbarId = inputId.substring(0, inputId.length - "name".length)
            + "pole";
    	
         if (data.status == "begin")
            Element.show(progressbarId);
         else if (data.status == "success")
            Element.hide(progressbarId);
      }
   }
}