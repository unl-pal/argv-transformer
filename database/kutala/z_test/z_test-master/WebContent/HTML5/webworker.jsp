<!DOCTYPE html>
<html lang="zh_CN">
<mate charset="utf-8"/>
<head></head>
<body>

<p>count:<output id="result"></output></p>
<button onclick="startWorker()">start work</button> 
<button onclick="stopWorker()">stop word</button>
<br><br>

<script>
var w;

function startWorker()
{
if(typeof(Worker)!=="undefined")
  {
  if(typeof(w)=="undefined")
  {
  	w=new Worker("demo_workers.js");
  }
  w.onmessage = function (event) {
    document.getElementById("result").innerHTML=event.data;
    };
  }
}

function stopWorker()
{ 
	w.terminate();
}
</script>

</body>
</html>
			