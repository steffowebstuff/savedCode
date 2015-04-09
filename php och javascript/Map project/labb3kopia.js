	//En klass som hämtar addresser för ett antal producenter och bygger en karta utifrån detta. 
	var myWindow = document.createElement("div");	
	//Global array som ska komma att innehålla producenterna. 
	var producersArray = new Array();

var labbscript = {
	run: function(){
		var unameField = document.getElementById("user");
		var passField = document.getElementById("pass");				
		var producersArea = document.getElementById("producers");
		var main = document.getElementById("main");
		this.myWindow = document.createElement("div");
		main.appendChild(myWindow);
		var producer = null;
		GetLinkedPictures();

						
		function GetLinkedPictures(){			
			$.ajax({  type: "GET",
			url: "http://localhost/webbteknik2/labb2/producers",  
			})  
			.done(function(msg) 
			{  
				for (var i = 0; i < 6; i++){
				    var prod = msg[i];
				    producersArray[i] = prod;
				    var url = "http:\/\/172.16.206.1\/~thajo\/1DV449\/laboration01\/producenter\/";
				    var webbaddress = url+prod.logotype; 
				    var producerDiv = document.createElement("div");
				    producerDiv.className = "producer";
				    var img = document.createElement("img");		
					var infoButton = document.createElement("button");
					infoButton.textContent = "Mer information";
					infoButton.id = i;
					infoButton.onclick = reply_clickButton;
					
					var name = prod.name;				
				    var producerTitle = document.createTextNode(prod.name);
				    producerDiv.appendChild(producerTitle);
				    img.className = "producerImg";
				    img.src= url +prod.logotype;
				    producerDiv.id = "producerDiv"+i;
				    producerDiv.appendChild(img);
				    producerDiv.appendChild(infoButton);
				    var producers = document.getElementById("producers");				    
				    producers.appendChild(producerDiv);				    
				}
				
				var moreProducers = document.getElementById("moreProducers");
				var linklist = document.createElement("ul");
				moreProducers.appendChild(linklist);
				for (var i = 6; i < msg.length; i++){
					prod = msg[i];
					producersArray[i] = prod;
					var url = "http:\/\/172.16.206.1\/~thajo\/1DV449\/laboration01\/producenter\/";
					var listemt = document.createElement("li");
					linklist.appendChild(listemt);
					var link = document.createElement("a");
				    link.textContent = prod.name;
					link.setAttribute('href', "#");
					link.id = i;
					link.onclick = reply_click;
					listemt.className = "listemt";
					listemt.appendChild(link);
					var brk = document.createElement("br");	
				}
				
			});

		}	
		
		function reply_click(e) {
		    e = e || window.event;
		    e = e.target || e.srcElement;
		    if (e.nodeName === 'A') {
		        //alert(e.id);
		        prod = producersArray[e.id];
		        showMap(prod.name, prod.address, prod.zipcode, prod.town, prod.website, prod.latitude, prod.longitude, e.id);
		    }
		}
		
		function reply_clickButton(e){
			e = e || window.event;
		    e = e.target || e.srcElement;
		    if (e.nodeName === 'BUTTON') {
		       // alert(e.id);	
				prod = producersArray[e.id];
		        showMap(prod.name, prod.address, prod.zipcode, prod.town, prod.website, prod.latitude, prod.longitude, e.id);
		    }
		}	

		function showMap(name, address, zipcode, town, src, latitude, longitude, i){
			var str = src;
			var pattern=/\http/;
			var prevPopup = document.getElementById("popup");
			if(prevPopup != null){
				myWindow.removeChild(prevPopup);
			}
			
			var popup = document.createElement("div");
			var info = document.createElement("div");
			var map = document.createElement("div");
			var header = document.createElement("h1");
			var webLink = document.createElement("a");
			webLink.setAttribute("href", src);
			var linkText = document.createTextNode(src); 
			webLink.appendChild(linkText);
			var brk = document.createElement("br");								
			var addressField = document.createElement("div");
			var mapAddress = document.createTextNode(name);
			var mapZipcode = document.createTextNode(zipcode);
			var mapTown = document.createTextNode(town);
			addressField.appendChild(mapAddress);
			addressField.appendChild(brk);
			addressField.appendChild(mapZipcode);
			addressField.appendChild(mapTown);
			
			var closeButton = document.createElement("button");
			var buttonText = document.createTextNode("Ta bort");
			closeButton.appendChild(buttonText);		
			closeButton.onclick = hideMap;
			closeButton.id = i;
				
			header.innerHTML = name;
						
			info.id = "info";
			map.id = "map";
			popup.id = "popup";
			myWindow.appendChild(popup);
			popup.appendChild(header);
			popup.appendChild(map);
			popup.appendChild(addressField);
			if (str.match(pattern)){
				popup.appendChild(webLink);
			}			
			popup.appendChild(brk);
			popup.appendChild(closeButton);
			
			//Googlekartan som skapas. Skapas inte om koordinater saknas. 
			if (GBrowserIsCompatible()) {
				if(!longitude){
					alert("Det finns inga koordinater för den här producenten");
				return false;
				}
		        var googlemap = new GMap2(map);		         
		        googlemap.setCenter(new GLatLng(latitude, longitude), 12);
		        googlemap.addControl(new GSmallMapControl());
		        googlemap.addControl(new GMapTypeControl());
		        googlemap.getBounds();	       			    
			    var point = new GLatLng(latitude, longitude);
				var marker = new GMarker(point);
				googlemap.addOverlay(marker);
			    
		    }

		}	
		
		function hideMap(e){
			e = e || window.event;
		    e = e.target || e.srcElement;
		    if (e.nodeName === 'BUTTON') {
				var popup = document.getElementById("popup");		
				myWindow.removeChild(popup);
		    }
		}						
	}
}

window.onload = labbscript.run;
