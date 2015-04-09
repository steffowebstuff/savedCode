<?php
//Uppgift12.php skapar en sqlite-tabell utifrån befintlig tabell. Notera att sökvägen är ändrad.
$response = file_get_contents("http://localhost/webbteknik2/uppgift22.php");
$doc = new DOMDocument; 
$doc->loadHTML($response);
$xpath = new DOMXpath($doc); 

$tr = $xpath->query("//table//tr"); 
$producers = array();

foreach ($tr as $row){
	$cells = $xpath->query("./td", $row);
	$producer = array();
	foreach($cells as $cell){
			$producer[] = $cell->nodeValue;
		}
	$producers[] = $producer;
}

array_shift($producers);

$database = new PDO('sqlite:UserDB.sqlite');
$database->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

$query = "CREATE TABLE IF NOT EXISTS producer12 (id INT, name TEXT, address TEXT, zipcode INT, town TEXT, logotype TEXT, website TEXT, latitude REAL, longitude REAL)";
$statement = $database->prepare($query);
if(!$statement->execute()){
	die("Fel vid tabellskapandet");
}

foreach ($producers as $row){
	try{
		var_dump($producers); 
			$query = "INSERT INTO producer12 (name, address, zipcode, town, logotype, website, latitude, longitude) VALUES ('$row[1]', '$row[2]', '$row[3]', '$row[4]', '$row[5]', '$row[6]','$row[7]', '$row[8]')"; 
			$statement = $database->prepare($query);
			if(!$statement->execute()){
				die("Fel vid tabellskapandet");
			}
	}
	
	catch(PDOException $e){
	 die("Fel" . $e->getMessage());
	}

}

