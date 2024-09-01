function doPopup(source) {
   country = source.form[source.form.id + ":country"];
   for ( var i = 0; i < country.length; i++) {
      if (country[i].checked) {
         popup = window.open("popup1.xhtml?country="
            + country[i].value, "popup",
         "height=300,width=200,toolbar=no,menubar=no,"
               + "scrollbars=yes");
         popup.openerFormId = source.form.id;
         popup.focus();
      }
   }
}

function doSave(value) {
   var formId = window.openerFormId;
   opener.document.forms[formId][formId + ":state"].value = value;
   window.close();
}
