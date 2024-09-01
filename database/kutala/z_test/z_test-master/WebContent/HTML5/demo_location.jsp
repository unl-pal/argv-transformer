<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
</head>
<body>
<p id="demo"></p>
<button onclick="getLocation()">check me!</button>
<script>
var x=document.getElementById("demo");
function getLocation()
  {
  if (navigator.geolocation)
    {
    navigator.geolocation.getCurrentPosition(showPosition);
    }
  }
function showPosition(position)
  {
  x.innerHTML="latitude: " + position.coords.latitude + 
  "<br>longitude: " + position.coords.longitude;	
  }
</script>
</body>
</html>