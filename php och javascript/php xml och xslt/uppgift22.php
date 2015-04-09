<?php
	$proc = new XSLTProcessor();
	$xsldoc = new DOMDocument(); 
	$xsldoc->load('uppgift22.xsl'); 	
	$proc->importStyleSheet($xsldoc); 
	$newXmldoc = new DOMDocument(); 
	$newXmldoc->load('labb12.xml');
	echo $proc->transformToXML($newXmldoc);  