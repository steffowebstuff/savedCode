<?php

class Validation {
	
	public function checkEmail($unCheckedEmail) {
		
		if(filter_var($unCheckedEmail, FILTER_VALIDATE_EMAIL)) {
			
			return true;
		}	
		return false;
	}
	
	public function checkUsername($unCheckedUsername) {
		//Vi kollar om användarnamnet är större än 4 och mindre eller lika med 20.
		if(strlen($unCheckedUsername) >= 6 && strlen($unCheckedUsername) <= 20) {
		
			//Vi kollar om några taggar har tagits bort, om så är fallet, returnera false.
			if($this->checkForTags($unCheckedUsername)) {
			
				return true;	
			}	
		}	
		return false;
	}
	
	public function checkPassword($uncheckedPassword1, $uncheckedPassword2) {
		//Jämför om båda lösenorden är likadana.
		if($uncheckedPassword1 == $uncheckedPassword2) {
			//Kollar längden på lösenordet
			if(strlen($uncheckedPassword1) > 5) {
				//Kollar så att inga taggar etc finns.
				if($this->checkForTags($uncheckedPassword1)) {			
					return true;
				}
			}
		}
		return false;
	}
	
	public function checkPersonNummer($uncheckedPersonNummer) {
		
		//Formatet 19900123-3890 , 9001233890, 900123-3890, 19900123-3890, 199001233890
		$regEx = '~^(((20)((0[0-9])|(1[0-2])))|(([1][^0-8])?\d{2}))((0[1-9])|1[0-2])((0[1-9])|(2[0-9])|(3[01]))[-]?\d{4}$~';
		if(preg_match($regEx, $uncheckedPersonNummer)) {
				
			$correctFormat = $this->createCorrectPersonNummer($uncheckedPersonNummer);
				
			if($this->luhn_check($correctFormat))
			{
				return $correctFormat;
			}
		}
		return false;
	}
	
	public function checkForTags($unStrippedString) {
		
		$strippedString = strip_tags($unStrippedString);
		//Om inga taggar har tagits bort så är strängarna likadana.
		if($strippedString == $unStrippedString) {
			
			return true;
		}
		return false;
	}
	
	public function checkDate($uncheckedDate) {
		
		//1990-01-23(10chars)
		$regEx1= "/^(\d){4}[-](\d){2}[-](\d){2}$/";
		//90-01-23(8 chars)
		$regEx2 = "/^[0-9]{2}-[0-9]{2}-[0-9]{2}$/";
		//900123(6 chars)
		$correctRegEx = "^[0-9]{6}$^";
	
		//Kollar om datumet är i något utav de tillåtna formaten.
		if(preg_match($regEx1, $uncheckedDate)||preg_match($regEx2, $uncheckedDate)||preg_match($correctRegEx, $uncheckedDate)) {
			
			//Omvanlar till rätt format via funktionen createCorrectDate.
			$correctformat = $this->createCorrectDate($uncheckedDate);
			
			return $correctformat;
		}
		return false;
	}
	
	//Luhn algoritm.
	public function luhn_check($number) {
		settype($number, 'string');
		$sumTable = array(
		array(0,1,2,3,4,5,6,7,8,9),
    	array(0,2,4,6,8,1,3,5,7,9));
		
    	$sum = 0;
    	$flip = 0;
  
    	for ($i = strlen($number) - 1; $i >= 0; $i--) {
	    	$sum += $sumTable[$flip++ & 0x1][$number[$i]];
	    }
	    return $sum % 10 === 0;
	}
	
	//Gör om datumet till 110101
	public function createCorrectDate($incorrectFormatDate) {
		$correctFormat="";
		
		//Gör ingenting (datumet är korrekt)
		if(strlen($incorrectFormatDate) == 6) {
			
			$correctFormat = $incorrectFormatDate;
		}
		//Tabort eventuella 2 första siffrorna och bindestrecken.
		if(strlen($incorrectFormatDate) == 10||strlen($incorrectFormatDate) == 8) {
			$correctFormat = substr($incorrectFormatDate, -8, 2);
			$correctFormat .= substr($incorrectFormatDate, -5, 2);
			$correctFormat .= substr($incorrectFormatDate, -2, 2);
		}

		return $correctFormat;
	}
	
	//Gör om personnummret till 9001233890
		public function createCorrectPersonNummer($incorrectFormatNumber) {
		$correctFormat="";
		
		//Gör ingenting (Personnumret är korrekt)
		if(strlen($incorrectFormatNumber) == 10) {
			
			$correctFormat = $incorrectFormatNumber;
		}
		//Tabort 2 första siffrorna
		if(strlen($incorrectFormatNumber) == 12) {
			
			$correctFormat = substr($incorrectFormatNumber, -10);
		}
		//Tabort 2 första siffrorna och bindestrecket
		if(strlen($incorrectFormatNumber) == 13) {
			
			
			$correctFormat = substr($incorrectFormatNumber, -11, 6);
			$correctFormat .= substr($incorrectFormatNumber, -4, 4);
		}
		//Tabort bindestrecket
		if(strlen($incorrectFormatNumber) == 11) {
			
			$correctFormat = substr($incorrectFormatNumber, -11, 6);
			$correctFormat .= substr($incorrectFormatNumber, -4, 4);
		}
		return $correctFormat;
	}
	
	function FormatTextString($string, $allowed = false){
		$string = preg_replace('@<script[^>]*>.+</script[^>]*>@i', "", $string); 
		 
		if($allowed){
			$string = strip_tags($string, "<p><b>");
		}
		else
		{
			$string = strip_tags($string);
		}
		return $string;
	}

	
	public function test()
	{
		
//EMAIL
		//Test av valid EMAIL
		$email = "sebastian.hgsfasaf@fsafa.se";
		if(!$this->checkEmail($email)) {
			
			echo "EMAIL i rätt format sebastian.hgsfasaf@fsafa.se, går inte igenom";
			return false;
		}
		//Test av felaktig EMAIL
		$emailWRONG = "fsafsaf.se";
		if($this->checkEmail($emailWRONG)) {
			
			echo "EMAIL i fel format fsafsaf.se, går igenom";
			return false;
		}
		
//USERNAME
		//Test av rätt format USERNAME
		$username = "adolfe";
		if(!$this->checkUsername($username)) {
			
			echo "USERNAME i rätt format adolfe, går inte igenom";
			return false;
		}
		$username = "ado";
		//För kort USERNAME
		if($this->checkUsername($username)) {
			
			echo "USERNAME i fel format ado, går igenom";
			return false;
		}
		$username = "adofsafsafasfasfasfasjfashfbsavfsavdghsavhdasvdvasgdgasdghas";
		//För långt USERNAME
		if($this->checkUsername($username)) {
			
			echo "USERNAME i fel format adofsafsafasfasfasfasjfashfbsavfsavdghsavhdasvdvasgdgasdghas, går igenom";
			return false;
		}
		$username = "<a>THAS</a>";
		//USERNAME with tags</h3>";
		if($this->checkUsername($username)) {
			
			echo "USERNAME i fel format <a>THAS</a>, går igenom";
			return false;
		}
		
//PASSWORD	
		//PASSWORD, två i rätt format och matchande.
		$password = "skitbra";
		$password2 = "skitbra";
		if(!$this->checkPassword($password, $password2)) {
			
			echo "PASSWORD i rätt format skitbra, skitbra, går inte igenom";
			return false;
		}
		$password = "skitbra";
		$password2 = "skitbraaaa";
		//PASSWORD, olika icke matchande.
		if($this->checkPassword($password, $password2)) {
			
			echo "PASSWORD i fel format skitbra, skitbraaaa, går igenom";
			return false;
		}
		$password = "skit";
		$password2 = "skit";
		//PASSWORD fel format, matchande men för kort.
		if($this->checkPassword($password, $password2)) {
			
			echo "PASSWORD fel format skit, skit, går igenom";
			return false;
		}
		$password = "<p>skitbra</p>";
		$password2 = "<p>skitbra</p>";
		//PASSWORD i fel format matchande, men med tagg
		if($this->checkPassword($password, $password2)) {
			
			echo "PASSWORD i fel format <p>skitbra</p>, <p>skitbra</p>, går igenom ";
			return false;
		}
//PERSONNUMMER
		$personnummer = "19900123-3890";
		//Test av PERSONNUMMER i fel format, ska omvandlas.(ska gå igenom)
		if(!$this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER rätt men i fel format 19900123-3890, ska omvandlas, går inte igenom";
			return false;
		}	
		$personnummer = "900123-3890";
		//Test av PERSONNUMMER i fel format, ska omvandlas.(ska gå igenom)
		if(!$this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER rätt men i fel format 900123-3890, ska omvandlas, går inte igenom";
			return false;
		}
		$personnummer = "19900123-3890";
		//Test av PERSONNUMMER i fel format, ska omvandlas.(ska gå igenom)
		if(!$this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER rätt men i fel format 19900123-3890, ska omvandlas, går inte igenom";
			return false;
		}
		$personnummer = "199001233890";
		//Test av PERSONNUMMER i fel format, ska omvandlas.(ska gå igenom)
		if(!$this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER rätt men i fel format 199001233890, ska omvandlas, går inte igenom";
			return false;
		}
		$personnummer = "9001233890";
		//Test av PERSONNUMMER i rätt format.
		if(!$this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER i rätt format 9001233890, går inte igenom";
			return false;
		}
		$personnummer = "900123-38901";
		//Test av PERSONNUMMER i fel format.
		if($this->checkPersonNummer($personnummer)) {
			
			echo "PERSONNUMMER i fel format 900123-38901, går igenom";
			return false;
		}
//DATUM
		$date = "1990-01-23";
		//DATUM i rätt format 1990-01-23
		if(!$this->checkDate($date)) {
			
			echo"DATUM i rätt format 1990-01-23, går inte igenom";
			return false;
		}
		$date = "90-01-23";
		//DATUM i rätt format 90-01-23
		if(!$this->checkDate($date)) {
			
			echo "DATUM i rätt format 90-01-23, går inte igenom";
			return false;
		}
		$date = "900123";
		//DATUM i rätt format 900123
		if(!$this->checkDate($date)) {
			echo "DATUM i rätt format 900123, går inte igenom";
			return false;
		}
		$date = "1990-01-23-3890";
		//DATUM i fel format 1990-01-23-3890
		if($this->checkDate($date)) {

			echo "FEL! DATUM i FEL format 1990-01-23-3890 går igenom";
			return false;
		}
//TEXT
		//Testar
		$evilString = '<script>alert(1);</script>Nice<p>detta funkar</p>';
		
		//Testar eviltaggen så det blir rätt?
		if($this->FormatTextString($evilString) != 'Nicedetta funkar')
		{
			echo "Jag kan göra massa fula saker med evilscript";
	
		}
		$string1 = '<b>Nice</b><p>Detta funkar</p></br>';
		//testar string 1 så den blir rätt
		if($this->FormatTextString($string1) != 'NiceDetta funkar')
		{
			echo "Jag kan göra massa fula saker string1";
	
		}
		//testar string 1 så den blir rätt
		if($this->FormatTextString($string1, true) != '<b>Nice</b><p>Detta funkar</p>')
		{
			echo "Jag kan göra massa fula saker string 1, true";

		}

	return true;
	}
}


