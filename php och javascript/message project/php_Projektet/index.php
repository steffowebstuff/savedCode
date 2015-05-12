<?php
session_start();
require_once("Controller/changeAuthorityController.php");
require_once("Controller/createusercontroller.php");
require_once("Controller/logincontroller.php");
//require_once("database.php");
require_once("DBconfig.php");
require_once("action.php");
require_once("View/navigationview.php");
require_once("Validation4.php");
require_once("masterpage.php");
require_once("Controller/writeMessageController.php");
require_once("Controller/searchController.php");

//Indexfilen som hela projektet renderas från.
class MasterController {
	public static function doControll() {
		$xhtml="";
		$cuc = new createUserController(); 
		$lc = new LoginController();
		$db = new DBConfig();
		$nw = new NavigationView(); 
		$links = "";
		$wmc = new WriteMessageController();
		$cac = new ChangeAuthorityController();
		$sfc = new SearchController();
			
		$validator = new Validation();
		$mp = new MasterPage();	
		
		//Sidan laddas.
		switch ($nw->getAction()){
			case action::removeMessage:
			$xhtml .= $wmc->WriteMessageControll($db, $validator);		
			$xhtml .= $lc->DoControll($db, $validator);
			$title = "Inloggad";
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());		
			return $page;
				
			case action::CREATE_NEW_USER:
			$title = "Skapa ny användare";
			$xhtml= $cuc->cucControll($db);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());	
			return $page;
			
			case action::CREATE_MESSAGE: 
			$title = "Skriva meddelande";
			$xhtml.= $wmc->WriteMessageControll($db, $validator);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());
			return $page;  
			
			case action::CHANGE_AUTHORITY: 
			$title = "Ändra auktoritet";
			$xhtml.= $cac->ChangeAuthorityControll($db, $validator);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());	
			return $page;
			
			case action::SWITCH_AUTHORITY: 
			$title = "Ändra auktoritet";
			$xhtml.= $cac->ChangeAuthorityControll($db, $validator);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());	
			return $page;
			
			case action::SEARCH:
			$title = "Sök filer";
			$xhtml.= $sfc->DoControll($db, $validator);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());	
			return $page;
			
			case action::SEARCHED:
			$title = "Sökresultat";
			$xhtml.= $sfc->DoControll($db, $validator);
			$page = $mp->GetVestaliaPage($title, $xhtml, $nw->createMenu());	
			return $page;
			
			default: 
			$xhtml .= $lc->DoControll($db, $validator);
			$title = "Inloggad";	
			if ($lc->GetLoginStatus()){
				$links = $nw->createMenu();
			}
			$page = $mp->GetVestaliaPage($title, $xhtml,$links);		
			return $page;
		} 
	}	
}

echo MasterController::doControll();
?>

