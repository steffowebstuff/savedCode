<?php
require_once("./View/searchView.php");
require_once("./Model/searchModel.php");

class SearchController{
	private $loggedIn = "loggedIn";
	private $username = "username";
	private $userId = "userId";
	 	
	 	public function DoControll($db, $validator){
		if(isset($_SESSION[$this->loggedIn])){		 		
	 		$body = "";
			$sm = new SearchModel($db);
	 		$sw = new SearchView();	
			
			if ($sw->HasMadeSearch()){
				$body .=  "Användaren har gjort en sökning."; 
				$searchPhrase = $sw->GetSearchPhrase();
				$username = $_SESSION[$this->username];
				$userId = $_SESSION[$this->userId];
				$messages = $sm->GetAllMessages($searchPhrase);
				$body .= $sw->ShowMessages($messages);
				$body .= $sw->SearchForm();				
				return $body;
			}			
			$body = $sw->SearchForm();	
			return $body;
		}	
		else {
			//Det här meddelandet ska komma in någon annanstans ifrån. 
			$body = "Du måste logga in för att kunna se den här sidan (Visa länk)";  //Kalla på den statiska funktionen ej inloggad
			return $body;
		}				
	}
}

