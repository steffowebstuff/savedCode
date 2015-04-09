<?php
require_once("./View/loginView.php");
require_once("./Model/loginHandler.php");

class LoginController{
	private $userId = null;
	private $loginStatus = false;
		
	
		//Användaren loggar in
	public function DoControll($db, $validator){
		$lh = new LoginHandler($db);
		$lw = new LoginView();
		
		$xhtml = $lw::Rubrik;
		$loginValidator = new Validation();
		
		if ($lw->TriedToLogout()){								
			$lh->RemoveUserSession();
			$lw->removeCookie();
			$xhtml .= $lw->userLoggedOut();  					
			return $xhtml;				
		}
		
		if(($username = $lw->getUserCookie()) != null && ($password = $lw->getPwCookie()) != null){
			if(($newCookie = $lh->DoCookieLogin($username, $password)) != null){
				//Ta bort medlem-koden
				if ($lw->RemoveUser()){
					$lh->Remove($lh->GetUserSession()); 
					$lw->removeCookie();
					$xhtml .= $lw->userLoggedOut();  					
					return $xhtml;		
				}
				$this->userId = $lh->GetId($username);
				$lw->removeCookie();
				$lw->createCookie($username, $newCookie);
				$this->loginStatus = true;
				$xhtml.= $lw->userLoggedIn();
				return $xhtml;
			}
		}	
		if($lh->IsLoggedIn($lh->GetUserSession(), $lh->GetPwSession())){
			//Ta bort medlem-koden
			if ($lw->RemoveUser()){
				$lh->Remove($lh->GetUserSession()); 
				$xhtml .= $lw->userLoggedOut();  					
				return $xhtml;		
			}
			$this->userId = $lh->GetId($lh->GetUserSession());
			$this->loginStatus = true;
			$xhtml.= $lw->userLoggedIn();
			return $xhtml;
		}
		if($lw->TriedToLogIn()){
			if($lh->DoLogin($lw->GetUserName(), $lw->GetPassword())){
				if($lw->rememberMe()){							
					$lw->createCookie($lw->GetUserName(), $lh->GenerateCookiePassword($lw->GetUserName())); 
				}
				$this->userId = $lh->GetId($lw->GetUserName());
				$this->loginStatus = true;
				$xhtml.= $lw->userLoggedIn();
				return $xhtml;			
			}
			//fel användaruppgifter		
		}
		
		$xhtml .= $lw->userLoggedOut();  					
		return $xhtml;			
	}
	public function GetLoginStatus(){
		return $this->loginStatus;
	}
	
	public function GetUserId(){
		return $this->userId;
	}
}
