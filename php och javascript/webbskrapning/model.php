<?php
//Modellklassen skapar funktionerna som kopplas till mysqlite. 
class Model{
	private $database;
	public function __construct(){
		$this->database = new PDO('sqlite:UserDB.sqlite');
	}
	
		public function Create($name, $address, $zipcode, $town, $website, $logotype, $longitude, $latitude){
		try {			
		$stmt = $this->database->prepare('INSERT INTO producer (name, address, zipcode, town, website, logotype, latitude, longitude) VALUES(?,?,?,?,?,?,?,?)');
		$stmt->bindParam(1, $name, PDO::PARAM_STR, 50);
		$stmt->bindParam(2, $address, PDO::PARAM_STR, 100);
		$stmt->bindParam(3, $zipcode, PDO::PARAM_STR, 50);
		$stmt->bindParam(4, $town, PDO::PARAM_STR, 50);
		$stmt->bindParam(5, $website, PDO::PARAM_STR, 50);
		$stmt->bindParam(6, $logotype, PDO::PARAM_STR, 50);
		$stmt->bindParam(7, $longitude);
		$stmt->bindParam(8, $latitude);
		$stmt->execute();
		}
		catch(PDOException $e){			
			die("Error: ". $e->getMessage());
			return false;
		}
		return true;
	}

	public function Update($PutArray, $id){
		try{
			$stmt = $this->database->prepare("UPDATE producer SET name=?, address=?, zipcode=?, town=?, website=?, logotype=?, latitude=?, longitude=? WHERE rowid=$id");
			$stmt->bindParam(1, $PutArray['name'], PDO::PARAM_STR, 50);
			$stmt->bindParam(2, $PutArray['address'], PDO::PARAM_STR, 100);
			$stmt->bindParam(3, $PutArray['zipcode'], PDO::PARAM_STR, 50);
			$stmt->bindParam(4, $PutArray['town'], PDO::PARAM_STR, 50);
			$stmt->bindParam(5, $PutArray['website'], PDO::PARAM_STR, 50);
			$stmt->bindParam(6, $PutArray['logotype'], PDO::PARAM_STR, 50);
			$stmt->bindParam(7, $PutArray['latitude']);
			$stmt->bindParam(8, $PutArray['longitude']);
			$stmt->execute();		
		}
		catch(PDOException $e){			
			die("Error: ". $e->getMessage());
			return false;
		}
		return true;
	}

	public function Delete($id){
		$query = "DELETE FROM producer WHERE id = $id";
		$stmt = $this->database->prepare($query);
		$stmt->execute();
	}

	public function GetProducer($id){
		if(!is_null($id)){			
			$sth = $this->database->prepare("SELECT * FROM producer WHERE id = $id");
			$sth->execute();
			$result = $sth->fetch(PDO::FETCH_ASSOC);
			$json = json_encode($result);
			return $json;			
		}
		$sth = $this->database->prepare("SELECT * FROM producer12");
		$sth->execute();	
		$result = $sth->fetchAll(PDO::FETCH_ASSOC);
		$json = json_encode($result);
		return $json;
	}
	
	public function GetPositions($id){
		if(!is_null($id)){
			$sth = $this->database->prepare("SELECT longitude, latitude FROM producer WHERE id = $id");
			$sth->execute();
			$result = $sth->fetch(PDO::FETCH_ASSOC);
			$json = json_encode($result);
			return $json;
		}
		
		$sth = $this->database->prepare("SELECT longitude, latitude FROM producer");
		$sth->execute();
		$result = $sth->fetchAll(PDO::FETCH_ASSOC);
		$json = json_encode($result);
		
		return $json;
		 
	}	
}
