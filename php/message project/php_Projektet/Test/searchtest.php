<?php
require_once("searchhandler.php");
require_once("validation.php");

private $m_mysqli;
$validator = new Validering();
$this->m_mysqli = new mysqli("localhost", "root", "krokodil", "member"); 	 
$this->m_mysqli->set_charset("utf8");  

//Ta reda p� hur m�nga meddelanden som finns i databasen
echo"<h1>Skriver in ett felaktigt meddelande</h1>";
GetAllMessages("N�gon s�kfras");
//Selectsats som tar reda p� hur m�nga meddelanden som finns i databasen.
echo"B�r inte komma ut n�gra meddelanden";

