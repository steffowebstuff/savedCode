<a href="?render=xhtml">Visa os-statistik i tabell</a>
<a href="?render=svg">Visa os-statistik i svg</a>
<a href="?render=xhtml_svg">Visa os-statistik i både svg och tabell</a>

<?php
	//PHP and svg. Renderar statistik baserat på xml information
	$filetype = "svg";
	if(isset($_GET['render'])){
		$filetype = $_GET['render'];
	}
	
	var_dump($filetype);
	$proc = new XSLTProcessor();
	$xsldoc = new DOMDocument(); 
	$xsldoc->load('uppgift3.xsl'); 		
	$proc->importStyleSheet($xsldoc); 
	
	$proc->setParameter("", "filetype", $filetype);	
	$newXmldoc = new DOMDocument(); 
	$newXmldoc->load('os_resultat.xml');
	echo $proc->transformToXML($newXmldoc);  
	
	
	
	 