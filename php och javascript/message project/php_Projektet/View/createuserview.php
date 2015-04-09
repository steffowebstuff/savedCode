<?php

class createUserView{
	const errorMessage = "Det blev fel på inloggningen"; 
	const shortErrorMessage = "För kort användarnamn";
	const shortPasswordMessage = "För kort lösenord";
	const samePasswordMessage = "Två olika lösenord";
	const alreadyExistingUser = "Användaren finns redan";
	const nowRegistered = "Användaren är nu registrerad";
	const indexLink = "<a href='index.php'>Tillbaka till index</a> ";
	const firstEvilScript = "Du skickade in en otillåten sträng i användar";
	const secondEvilScript = "Du skickade in en otillåten sträng i första passwordformuläret";
	const thirdEvilScript = "Du skickade in en otillåten sträng i andra passwordformuläret";
	
	public $BecomeMember= "BecomeMember";
	public  $CreateMember= "CreateMember";
	public  $Username= "Username";
	public  $Password1= "Password1";
	public  $Password2= "Password2";
	
	public function successfulRegistration(){
		$html = "";
		$html.= self::nowRegistered;
		$html.= self::indexLink;
		return $html;
	}
	
	
	
	public function NewMemberBox() {
		$Username="";		
	  	return '<form action="index.php?'.NavigationView::action.'='.action::CREATE_NEW_USER.'" method="post">
	    <fieldset>
	    Username: <input type="text" name="Username" value="" /><br />
		Password: <input type="text" name="Password1" value="" /><br />
		Repeat Password: <input type="text" name="Password2" value="" /><br />
		
	   	<input type="submit" name="CreateMember" value="Skapa medlem" />
	   	</fieldset>
	   	</form>';
  	}
	
	public function CreateNewMember(){
	  	if (isset( $_POST[$this->CreateMember])) {    
	      return true;
		}
		else {
			return false;
		}
  	}
	
	public function GetNewUsername(){
	  	if (isset( $_POST[$this->Username])) {
	      
	      return $_POST[$this->Username];
	    }
		else {
			return null;
		}
	}
	
	public function GetFirstPassword(){
	  	if (isset( $_POST[$this->Password1])) {
	      
	      return $_POST[$this->Password1];
	    }
		else {
			return null;
		}
  	}
	
	public function GetSecondPassword(){
	  	if (isset( $_POST[$this->Password2])) {
	      
	      return $_POST[$this->Password2];
	    }
		else {
			return null;
		}
	 }	
}


