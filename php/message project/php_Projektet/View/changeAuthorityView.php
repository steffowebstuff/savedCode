<?php
class ChangeAuthorityView{
	//Strängberoenden borttagna
	private $switchButton = "SwitchButton";
	private $username = "Username";
	private $P_Id = 'P_Id';
	
	 public function GetAllMembers($members, $enemies){
	  	
	  	if(is_Array($members)){
	  		$html = '';
	  		foreach($members as $member){
	  			if(in_array($member['P_Id'], $enemies)){
					$html .= $this->MemberShow($member, false);
				}
				else{
					$html.=$this->MemberShow($member);
				}			
	  		}
			return $html;
	  	}
		$html = "Inga meddelanden hittades";
		return $html;
	 }
	 
	  public function MemberShow($member, $isFriend = true){
	  	return"<div class='Member'>  	
	  		<p>Användaren ".$member[$this->username]."<a href='?".NavigationView::action."=".action::SWITCH_AUTHORITY  ."&membernumber=".$member[$this->P_Id]."'><img class='redImage' src=".$this->FriendPicture($isFriend).">Skifta</a></p>	  		
	  		</div>
	  	";		
	  }  
	  
	  public function FriendPicture($isFriend){
	  	if($isFriend){
	  		return"greenCheck.png";
	  	}
	  	return"redX.png";
	  } 
}