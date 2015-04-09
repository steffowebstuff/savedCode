<?php
require_once("./View/createuserview.php");
require_once("./Model/memberhandler.php");
class createUserController{
	
	public function cucControll($db){
		$output= "";
			
		$cuw = new createUserView();
		$name = "mittNamn";
		
		$user = $cuw->Username;
		$firstPassword = $cuw->Password1;
		$secondPassword = $cuw->Password2;
			
		//Sidan hämtas men inte från formuläret	
		if ($_SERVER['REQUEST_METHOD'] == 'GET'){ 

			$output.= $cuw->NewMemberBox();
			$user = $cuw->Username;
			$firstPassword = $cuw->Password1;
			$secondPassword = $cuw->Password2;
			$cuw->createNewMember($user, $firstPassword);
			
		  }
		 else {
			$user = $cuw->GetNewUsername();
			$fPassword = $cuw->GetFirstPassword();
			$sPassword = $cuw->GetSecondPassword();
			

			$mh = new MemberHandler($db);				
			$validator = new Validation();
			$userBeforeValidation = $user;	
			$fPasswordBeforeValidation = $fPassword;	
			$sPasswordBeforeValidation = $sPassword;		
			$user = $validator->FormatTextString($user); 
			$fPassword = $validator->FormatTextString($fPassword); 
			$sPassword = $validator->FormatTextString($sPassword); 
			
			//Validerar för elaka script
			if ($userBeforeValidation != $user){
				$output .= $cuw::firstEvilScript; 
			}
			
			if ($fPasswordBeforeValidation != $fPassword){
				$output .= $cuw::secondEvilScript; 
			}
			
			if ($sPasswordBeforeValidation != $sPassword){
				$output .= $cuw::thirdEvilScript; 
			}
			
			//Kollar att de inskickade uppgifterna är giltiga		
			$validUsernameTest = $mh->ValidUsername($user);			
			if ($validUsernameTest == false){				
				$output .= $cuw::shortErrorMessage.$cuw->NewMemberBox();
				return $output;
	 		}
						
			$ValidPasswordTest = $mh->ValidPasswordCheck($fPassword);
			if ($ValidPasswordTest == false){
				$output .= $cuw::shortPasswordMessage.$cuw->NewMemberBox();
				return $output;
			}
						 
			$samePasswordTest = $mh->samePasswordCheck($fPassword, $sPassword);
			if ($samePasswordTest == false){
				$output .= $cuw::samePasswordMessage.$cuw->NewMemberBox();
				return $output;
			}
			
			
			$mh->existingUsernameCheck($user);
			if($mh->existingUsernameCheck($user) ==true){
				$output .=$cuw->NewMemberBox().$cuw::alreadyExistingUser;
				return $output;
			}
			
			$mh->NewInsert($user, $fPassword);
			$output .= $cuw->successfulRegistration();
			return $output;
 		}
	return $output;
	}
}	

