<?php 
class ChangeAuthorityModel{
	//Strängberoenden fixade
	private $m_mysqli;
	private $username = 'username';
	private $userId = 'userId';
	private $BlockedId = 'BlockedId';

	public function __construct($db) {
        $this->m_mysqli = new mysqli($db->m_host, $db->m_user, $db->m_pass, $db->m_db);
		$this->m_mysqli->set_charset("utf8");
    }
		
	public function GetAllMembers(){
		$username =  $this->username;
		$membersArray = Array();			
		$query = "SELECT Username, P_Id FROM Members2";	
		$result = $this->m_mysqli->query($query);
		while ($row = $result->fetch_array()){
			$membersArray[] = $row;
		} 
		return $membersArray;
	}
	
	public function BlockedEnemies($userId){
		$enemies =  Array();
		$stmt = $this->m_mysqli->prepare("SELECT `BlockedId` FROM access2 WHERE `UserId` = ?");	
		$stmt->bind_param("i", $userId);
		$stmt->execute();
		$result = $stmt->get_result();
		while ($row = $result->fetch_array()){
			$enemies[] = $row[$this->BlockedId]; 
		}	
		return $enemies;
	}
	
	public function Block($receiverId){
		$userId = $_SESSION[$this->userId];
		$enemies = $this->BlockedEnemies($userId);
		if (!in_array($receiverId, $enemies)){
			$stmt = $this->m_mysqli->prepare("INSERT INTO `access2`(UserId, BlockedId) VALUES(?, ?)");
			$stmt->bind_param("ii", $userId, $receiverId);
			$stmt->execute();
			$stmt->close();
		}	
		else{
			$stmt = $this->m_mysqli->prepare("DELETE FROM `access2`WHERE UserId=? AND BlockedId = ?");			
			$stmt->bind_param("ii", $userId, $receiverId);
			$stmt->execute();
			$stmt->close();
		}
	}	
}
