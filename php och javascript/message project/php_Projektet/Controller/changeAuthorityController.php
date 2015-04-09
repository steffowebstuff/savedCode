<?php
require_once("./View/changeAuthorityView.php");
require_once("./Model/changeAuthorityModel.php");
class ChangeAuthorityController{
	
	
	private $userId = "userId";
	private $membernumber = "membernumber";
	
	public function ChangeAuthorityControll($db, $validator){
		if(isset($_SESSION[$this->loggedIn]))
	 	{	 		
	 		$body = "";
			$cam = new ChangeAuthorityModel($db);
	 		$caw = new ChangeAuthorityView();						
			$userId = $_SESSION[$this->userId];
				
			if (isset($_GET[$this->membernumber])){
	 			$cam->Block($_GET[$this->membernumber]);
	 		}
			
			$enemies = 	$cam->BlockedEnemies($userId);			
			$members = ($cam->GetAllMembers());	
			$body .= $caw->GetAllMembers($members, $enemies); 			
			
			return $body;
		}	
		else {
			//echo "Du måste logga in för att kunna se den här sidan (Visa länk)";	//Den här ska bytas ut mot en statisk funktion, motsvarande echoBr, som skickas ut i else-satsen.
		}	
	}
}