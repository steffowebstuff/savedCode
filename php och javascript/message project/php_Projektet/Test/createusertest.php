<?php
require_once("memberhandler.php");


//private $m_mysqli;


$this->m_mysqli = new mysqli("localhost", "root", "krokodil", "member"); 	 
$this->m_mysqli->set_charset("utf8");      	        

$mh = new memberhandler();
$result = $mh->NewInsert("somedude", "");
var_dump($result);





