<?php 
class LoginHandler{
	public $userId = "userId";
	private $username = "username";
	private $password = "password";
	private $sessionLoggedIn = "loggedIn";
	private $sessionUsername = "anvNamn";
	private $members2 = "members2";
		
	public function __construct(DBConfig $config) { 
		      $this->m_mysqli = new mysqli($config->m_host, $config->m_user, $config->m_pass, $config->m_db); 
		      $this->m_mysqli->set_charset("utf8");
	}
	public function GetUserSession(){
		return (isset($_SESSION[$this->username])) ?  $_SESSION[$this->username] : null;
	}
	public function GetpwSession(){
		return (isset($_SESSION[$this->password])) ?  $_SESSION[$this->password] : null;
	}	
	public function RemoveUserSession(){
		session_unset();
	}
	public function Remove($name) { 
		$members2 = $this->members2;
        $sql = "DELETE FROM ". $members2 . " WHERE username = ?";  		    
        $stmt = $this->m_mysqli->prepare($sql); 
        $stmt->bind_param("s", $name);       
        $stmt->execute();       
        $stmt->close();
        return true;
     } 	
	public function DoLogin($username, $password){
		if($this->IsLoggedIn($username, $password)){
			$_SESSION[$this->username] = $username; 
			$_SESSION[$this->password] = $password; 
			return true;
		}
		return false;
	}
	public function IsLoggedIn($username, $password)
	{
		$stmt = $this->m_mysqli->prepare("SELECT * FROM `members2` WHERE `Username` = ? AND `Password` = ?");
		$stmt->bind_param("ss", $username, $password); 
		$stmt->execute();
		$stmt->store_result();
		if($stmt->num_rows > 0){		
		 	return  true;
		 }
		else {
			return false;
		}					
	}
	
	public function GetId($username)
	{
		 $query = $this->m_mysqli->prepare("SELECT `P_Id` FROM members2 WHERE `username` = ?");
		 $query->bind_param("s", $username);  
		 $query->bind_result($userId);
		 $query->execute();
		 if($query->fetch()){		  
			 return $userId;	  
		 }	
		 return null;
	
	}
	public function GenerateCookiePassword($username){
		
		$length = 8;
		$str = (string)NULL;
		$charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
     	$count = strlen($charset);
     	
     	while ($length--) {
         	$str .= $charset[mt_rand(0, $count-1)];
     	}
		$stmt = $this->m_mysqli->prepare("UPDATE members2 SET tempPassword = ? WHERE username = ?");
		$stmt->bind_param("ss", $str, $username);
		$stmt->execute();
		$stmt->close();
  		
	   return $str;

	}
	
	public function DoCookieLogin($username, $password){		
		
	  	$sql = "SELECT * FROM `members2` WHERE `username` = ? AND `tempPassword` = ?"; 
	    $stmt = $this->m_mysqli->prepare($sql);
		$stmt->bind_param("ss", $username, $password);
		$stmt->execute();
		$stmt->store_result();
			
		if($stmt->num_rows > 0){
			
			$newCookie = $this->GenerateCookiePassword($username);
			return $newCookie;
			
		}
		
		$stmt->close();
		return null;
		
	}
	
}

