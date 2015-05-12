<?php
class MemberHandler{
		private $m_mysqli;
		private $members2 = "members2";
	
		public function __construct($db) { 
			$this->m_mysqli = new mysqli($db->m_host, $db->m_user, $db->m_pass, $db->m_db); 	 
			$this->m_mysqli->set_charset("utf8");      	        
		}
		  		  
		public function Remove($name) { 
			$members2 = $this->members2;
            $sql = "DELETE FROM ". $members2 . " WHERE username = ?";           
            $stmt = $this->m_mysqli->prepare($sql);           
            if ($stmt === FALSE) {
                    return false;
            }		
            if ($stmt->bind_param("s", $name) === FALSE) {
                    $stmt->close();
                    return false;
      		}       
            if (!$stmt->execute()) {
                        $stmt->close();
                        return false;
            }
            $stmt->close();
            return true;
        } 
		  		  
		public function NewInsert($name, $password) {	
			$members2 = $this->members2;
            $sql = "INSERT INTO " . $members2 . "(Username, Password, tempPassword) VALUES(?, ?, 0)"; 
            $stmt = $this->m_mysqli->prepare($sql);         
            if ($stmt === FALSE) {
                    return false;
            }        
            if ($stmt->bind_param("ss", $name, $password) === FALSE) {

                    $stmt->close();
                    return false;
            }        
	        if ($stmt->execute()) {
	        } 
            else {
                    $stmt->close();
                    return false;
            }            
	        $stmt->close();
	        return true;
	    }
		  		
		public function samePasswordCheck($firstPassword, $secondPassword){
			if ($firstPassword != $secondPassword){
				return false;
			}
			return true;
		}
	
		public function ValidPasswordCheck($password){
			if (strlen($password) < 6){
				return false;
			}
			return true;
		}
	
		public function existingUsernameCheck($username){			
			$members2 = $this->members2;
			$sql = "SELECT * FROM ". $members2 ." WHERE Username = ?";            
			$stmt = $this->m_mysqli->prepare($sql); 
			$stmt->bind_param("s", $username);
			$stmt->execute();
			$stmt->store_result();
			if($stmt->num_rows >0){
				return true;
			}
			return false;
		}
		
		public function validUsername($username){
			if (strlen($username) < 1){
				return false;
			}
			return true;
		}	
}
