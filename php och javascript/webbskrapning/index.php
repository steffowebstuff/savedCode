<!-- Ajaxanrop till php-labben -->
<!--Indexfilen gör anropet som avgör vilken metod som ska användas etc -->
<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.6.4.js"></script>
	</head>
	<body>
		<script type="text/javascript">
		//Javacriptfil för Crud-anrop
		//Insertanrop
		/*
		$.ajaxSetup({
				url: "http://localhost/webbteknik2/labb2/producers",
				type: "POST",
				headers: { "Accept": "application/json"},
				data: {'name': 'skurken', 'address': 'Finkan 25', 'zipcode': '4848', 'town': 'storstan', 'website':'???', 'logotype': '!!!', 'latitude': '121.1', 'longitude' : '4848.3' }							
		});
		*/
		
			
		$.ajaxSetup({
			url: "http://localhost/webbteknik2/labb2/producers/14",
			type: "PUT",
			headers: { "Accept": "application/json"},
			data: {'name': 'Steffe', 'address': 'gatan50', 'zipcode': '4848', 'town': 'storstan', 'website':'???', 'logotype': '!!!', 'latitude': '3838', 'longitude' : '48459' }							
		});		
		
			
		//Selectanrop
		/*
			$.ajaxSetup({
				//url: "http://localhost/webbteknik2/labb2/producers/16/position", 				
				//url: "http://localhost/webbteknik2/labb2/producers/position",
				//url: "http://localhost/webbteknik2/labb2/producers",
				url: "http://localhost/webbteknik2/labb2/producers/45", 
				type: "GET",
				headers: { "Accept": "application/json"}
			});	
		*/
			
				
			
			//Deleteanrop
			/*
			$.ajaxSetup({
				url: "http://localhost/webbteknik2/labb2/producers/170",
				type: "DELETE",
				headers: { "Accept": "application/json"},
				//data: {'id': '103', 'name': 'Stefan', 'address': 'gatan2', 'zipcode': '4848', 'town': 'storstan', 'website':'???', 'logotype': '!!!', 'latitude': '121.1', 'longitude' : '4848.3' }					
			});
			*/
			
			$.ajax ({
				success: function(data){
					console.log("working");
					console.log(data);
					
				},
				
				error: function(object, error){
					console.log("not working");
					console.log(error);
				}
			});
		</script>
	</body>
</html>