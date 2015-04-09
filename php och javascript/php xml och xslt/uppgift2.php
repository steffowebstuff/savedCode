<?php
	//Example of xslt coding file with php
	$proc = new XSLTProcessor();
	$xsldoc = new DOMDocument(); 
	$xsldoc->load('uppgift2.xsl'); 	
	$proc->importStyleSheet($xsldoc); 
	$newXmldoc = new DOMDocument(); 
	$newXmldoc->load('labb1.xml');
	echo $proc->transformToXML($newXmldoc);  