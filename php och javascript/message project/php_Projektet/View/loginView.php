<?php
//TODO Fixa id-variabel i sessionen
class LoginView {
	
	private $Username= "Username";
	private $Password= "Password";
	private $LoginButton= "LoginButton";
	private $LogoutButton= "LogoutButton";
	private $Remember= "Remember";
	private $RemoveButton = "RemoveButton";
	
	//Konstanterna
	const Brk = "<br/>";
	const Loggedout = "Du har just loggat ut";
	const Utloggad = "Du är utloggad";
	const Inloggad = "Du är inloggad";
	const FailedLogin= "Du misslyckades med inloggningen";
	const Rubrik = "<p>Logga in</p> ";
	const firstEvilScript = "Du skickade in en otillåten sträng i användar";
	const secondEvilScript = "Du skickade in en otillåten sträng i första passwordformuläret";
	const removeUserMessage = "Användaren är nu borttagen";
	
	public function userLoggedOut(){
		$html = $this->DoLoginBox();
		$html.= self::Loggedout; 
		$html.= self::Brk;
		return $html;
	}
	
	public function userLoggedIn(){
		$html = $this->DoLogoutBox();
		$html.= self::Inloggad; 
		$html.= self::Brk;
		$html.= $this->RemoveButton();
		return $html;
	}
	
	public function removeCookie(){
		setcookie($this->Username, "", time()-3600);
		setcookie($this->Password, "", time()-3600);
	}
	
	public function createCookie($Username, $Password){
		setcookie($this->Username, $Username, time()+3600);
		setcookie($this->Password, $Password, time()+3600);
	}
	
	public function getUserCookie(){
		if (isset($_COOKIE[$this->Username]))
		{
			return $_COOKIE[$this->Username];
		}
		return null;			
	}
	
	public function getPwCookie(){
		if (isset($_COOKIE[$this->Password]))
		{
			return $_COOKIE[$this->Password];
		}
		return null;			
	}
	
		
	public function rememberMe(){
		if (isset( $_GET[$this->Remember])) {      
		      return $_GET[$this->Remember];
		}
		else {
			return null;
		}				
	}
			
	public function DoLoginBox() {
		//Den här ska ändras till post.		
	    return 
	    '<div class="formArea">
		    <form method="get">
			    <fieldset>
				    User name: <input type="text" name="'.$this->Username.'" value="'.$this->GetUserCookie().'" /><br />
					Password: <input type="password" name="'.$this->Password.'" value="'.$this->GetPwCookie().'" /><br />
					
				   <input type="checkbox" name="Remember" value="Remember" /> Remember me
				   <input type="submit" name="LoginButton" value="Log in" />
			   </fieldset>
		   </form>
		   <a href="/php/projektet/?action=1">Bli medlem?</a>
	   </div>'
	   ;
  	}
  
	public function DoLogoutBox() {
	   return '
		   <div class="formArea">
		   <form name="input"  method="get">
		   <input type="submit" name="LogoutButton" value="LogOutButton" />
		   </form>
	   </div>
	   ';
	}
  
	public function RemoveButton() {
	    return '<form name="input"  method="get">
	    <input type="submit" name="RemoveButton" value="Ta bort medlem" />
	    </form>';
	}
	 
	public function RemoveUser(){
	  	if (isset( $_GET[$this->RemoveButton])) {
			self::removeUserMessage; 
			return $_GET[$this->RemoveButton];
	    }
		return null;
	}
	  
	public function GetUserName(){
	  	if (isset( $_GET[$this->Username])) {	      
			return $_GET[$this->Username];
	    }
		return null;
	}
	  
	public function GetPassword(){
	  	if (isset( $_GET[$this->Password])) {
	      
	      return $_GET[$this->Password];
	 	}
		return null;
	}
	  
	public function TriedToLogIn(){
		if (isset( $_GET[$this->LoginButton]) ) { 
			return true;
		}
		return false;
	}
	public function TriedToLogOut(){
		if (isset( $_GET[$this->LogoutButton])) {
			return true;
		}
		return false;
	}
	  
	public function PersonalInformation($userInput){
		return"<p>Användarnamnet är $userInput</p>";
	}	 
	  
}
  