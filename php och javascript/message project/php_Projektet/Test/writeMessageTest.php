<?php
require_once("writemessagehandler.php");
require_once("validation.php");

private $m_mysqli;
$validator = new Validering();
$this->m_mysqli = new mysqli("localhost", "root", "krokodil", "member"); 	 
$this->m_mysqli->set_charset("utf8");  

//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.
echo"<h1>Skriver in ett riktigt meddelande</h1>";
SaveWrittenMessage($1, $2, $"Hej p� dig");
//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.
echo"B�r vara ett meddelande mer nu";

//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.
//H�r kan inneb�ra f�r m�nga tecken, ful kod och anv�ndare som inte finns.
echo"<h1>Skriver in ett felaktigt meddelande</h1>";
SaveWrittenMessage($1, $2, $"N�got d�ligt");
//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.
echo"B�r vara lika m�nga meddelanden nu";

echo"<h1>Tar bort ett meddelande</h1>";
$messageId = 1;
$stmt = $this->m_mysqli->prepare("DELETE FROM messages3 WHERE Messages_Id = ?");
		$stmt->bind_param("i", $messageId);
		$stmt->execute();
		$stmt->close();
//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.		
echo"B�r vara ett meddelande mindre nu";
