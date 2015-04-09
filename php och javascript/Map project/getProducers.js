//Ajaxanropet till php-klassen som hämtar producenterna.
function getProducers(str){
var ajaxRequest;  
 
 try
	 {
	  // Opera 8.0+, Firefox, Safari
	  ajaxRequest = new XMLHttpRequest();
	 }
	  catch (e){
  // Internet Explorer Browsers
  try
	{
	   ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
	}   
	 catch (e) {
   try
   {
    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
   } 
   	catch (e){
    alert("Your browser broke!");
    return false;
   }
  }
 }

 ajaxRequest.onreadystatechange = function(){
  if(ajaxRequest.readyState == 4)
  {
	if(ajaxRequest.status == 200){	
	  document.getElementById("test").innerHTML = ajaxRequest.responseText;
	  }
  }
 }
 ajaxRequest.open("GET","getProducers.php",true);
 
 ajaxRequest.send(null); 
}
