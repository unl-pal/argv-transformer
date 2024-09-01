if (!com) var com = {};
if (!com.corejsf) com.corejsf = {};
com.corejsf.spinner = {
   spin: function(field, increment) {
	  var v = parseInt(field.value) + increment;
	  if (isNaN(v)) return;
	  if ('min' in field && v < field.min) return;
	  if ('max' in field && v > field.max) return;
	  field.value = v;
   }
};
