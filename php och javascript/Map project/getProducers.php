<?php
echo "testar getProducers.php";
//Den här klassen ska köra en select-query som hämtar alla raderna ifrån producers-tabellen och sedan returnerar svaret. 

$database = new PDO('sqlite:UserDB.sqlite');  
//$database->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

$sth = $database->prepare("SELECT * FROM producer3");
$sth->execute();
$query = "CREATE TABLE IF NOT EXISTS producer4 (id INT, name TEXT, address TEXT, zipcode INT, town TEXT, website TEXT, logotype TEXT, latitude REAL, longitude REAL)";
$statement = $database->prepare($query);
if(!$statement->execute()){
	die("Fel vid tabellskapandet");
}
echo "...";

