<?php
class WriteMessageView{
	private $Content= "Content";
	private $SendButton= "SendButton";
	private $Receiver= "Receiver";
	private $UserListButton = "UserListButton";
	private $removeMessage = 'removeMessage';
	
	const NoContent = "Inget meddelande fanns";
	const NoMembers = "Inga medlemmar hittades";
	const NoMessages = "Inga meddelanden hittades";
	const SentMessage = "Meddelandet har skickats";

	public function WriteMessageForm(){
		return '<form action="index.php?'.NavigationView::action.'='.action::CREATE_MESSAGE.'" method="post">
		Receiver <input type="text" name="Receiver" />
		Content <input type="textarea" name="Content" />
	    <input type="submit" name="SendButton" value="Skicka" />
	    </form>';
	}
	
	public function HasSentMessage(){
	  	if(isset( $_POST[$this->Content])) {	      
	      return true;
	    }
		return self::SentMessage;
	}
	
	public function GetContent(){
		if (isset( $_POST[$this->Content])){
			return $_POST[$this->Content];
		}
		return self::NoContent;
	}
	
	Public function GetReceiver(){
		if (isset( $_POST[$this->Receiver])){
			return $_POST[$this->Receiver];
		}
		return null;
	}
	
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
		return self::NoMembers;
	 }
	 
	  public function MemberShow($member, $isFriend = true){
	  	return"<div class='Member'>  	
	  		<p>Användaren ".$member['Username']."<img class='redImage' src=".$this->FriendPicture($isFriend)."></p>
	  		</div>";
	  }  
	  
	 
	 public function WantsUserList(){
		if (isset( $_POST[$this->UserListButton])){
			return true;
		}
		else {
			return null;
		}
	}
	 
	 
	 public function UserListButton(){
		return '<form action="index.php?'.NavigationView::action.'='.action::CREATE_MESSAGE.'" method="post">
	    <input type="submit" name="UserListButton" value="Alla användare" />
	    </form>';
	 }
	 
	 public function FriendPicture($isFriend){
	  	if($isFriend){
	  		return"greenCheck.png";
	  	}
	  	return"redX.png";
	  } 
	 
	 public function GetMessages($messages){
	  	
	  	if(is_Array($messages)){
	  		$html = '';
	  		foreach($messages as $message){
	  			$html.=$this->StandardMessage($message);			
	  		}
			return $html;
	  	}
		return self::NoMessages;
	 }
	  
	  public function StandardMessage($message){
	  	return
	  	"<div class='Message'>
	  		".$this->RemoveMessageLink($message['Messages_Id'])."
	  		<br />
	  		<a href='#' class='right'>Ändra</a>
	  		<p>Användare ".$message['Sender']." till ".$message['Receiver'] ."med innehållet ".$message['Content']."</p>
	  		<a>Kommentera</a>
	  	</div>
	  	"; 
	  }  
	  
	  public function GetMessageId(){
	  	if(isset($_GET[$this->removeMessage])){
	  		return $_GET[$this->removeMessage];
	  	}
		 return null;
	  }
	  
	  
	//Ska skrivas ut från varje meddelande. Lägg sedan till action. 
	 public function RemoveMessageLink($messageId){
	 	return "<a class='right' href='?".NavigationView::action."=".action::removeMessage."&removeMessage=$messageId'>Ta bort mig</a>";
	 }
}
