<?php

class SearchModel{
	public function __construct(DBConfig $config) { 
			  $this->m_mysqli = new mysqli($config->m_host, $config->m_user, $config->m_pass, $config->m_db); 
			  $this->m_mysqli->set_charset("utf8");
	}
	
	public function GetAllMessages($searchPhrase){
		$userId = $_SESSION['userId'];
		$messagesArray = Array();	
		$searchPhrase = '%'.$searchPhrase.'%';		
		$stmt = $this->m_mysqli->prepare("SELECT `Messages_Id`, `Sender`, `Content`, `Receiver`  FROM Messages3 WHERE `Content` LIKE ? AND `Sender` LIKE ? OR `Receiver` LIKE ?");
		echo $searchPhrase;
		$stmt->bind_param("sii", $searchPhrase, $userId, $userId);  
		$stmt->execute();
		$result = $stmt->get_result();
		while($row = $result->fetch_array()){
			$messagesArray[] = $row;
		}
		
	    $stmt->close(); 
		return $messagesArray;
	}
	
}