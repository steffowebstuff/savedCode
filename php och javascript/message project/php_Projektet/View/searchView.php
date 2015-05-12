<?php

class SearchView{
		
	const NoMessages = "Inga meddelanden hittades";
	private $searchInput = "searchInput";
	private $searchButton = "searchButton";

	public function SearchForm(){
		return '<form action="index.php?'.NavigationView::action.'='.action::SEARCHED.'" method="post">
		Sök <input type="text" name="'.$this->searchInput.'" />
		<input type="submit" name="'.$this->searchButton.'" value="Skicka" />
	    </form>';
	}
	
	public function HasMadeSearch(){
		if (isset( $_POST[$this->searchButton])) {	      
	      return true;
	    }
		return false;
	}
	
	public function GetSearchPhrase(){
		if(isset($_POST[$this->searchInput])){
			return $_POST[$this->searchInput];
		}
		return null;
	}
	
	public function ShowMessages($messages){  	
	 	if(is_Array($messages)){
	  		$html = '';
	  		foreach($messages as $message){
	  			$html.=$this->StandardMessage($message);			
	  		}
			return $html;
	  	}
		$body = self::NoMessages;
		return $body;
	}
	  
	public function StandardMessage($message){
	  	return
	  	"<div class='Message'>
	  		<br />
	  		<p>Användare ".$message['Sender']." till ".$message['Receiver'] ."med innehållet ".$message['Content']."</p>
	  	</div>
	  	"; 
	} 	
	  
	
}