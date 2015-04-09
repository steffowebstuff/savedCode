<?php
require_once("loginHandler.php");
require_once("loginView.php");
require_once("DBconfig.php");



	$db = new DBConfig();
	$lw = new LoginView();
	$lh = new LoginHandler($db);
	
	echo"good password, return true";
	var_dump($lh->NewDoLogin("stefan", "stefantest"));
	echo "<br />";
	
	echo"bad password, return false";
	var_dump($lh->NewDoLogin("ingen", "ingenpass"));
	echo "<br />";
	
	echo"Testar elak kod 1";
	var_dump($lh->NewDoLogin("<script>", "..."));
	echo "<br />";
	
	
	
	
	
	