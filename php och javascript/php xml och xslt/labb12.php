<?php
//Webbskrapningsfil som hämtar producenterna och skapar XML och JSON-filer.
	$webLink = "http://172.16.206.1/~thajo/1DV449/laboration01/producenter/";
	$response = file_get_contents("http://172.16.206.1/~thajo/1DV449/laboration01/producenter/producenter_lab3.html");
	
	$doc = new DOMDocument; 
	$doc->loadHTML($response);
	$xpath = new DOMXpath($doc); 
	
	$links = $xpath->query("//table//a"); 
	$pages = array();
	
	for ($i=0; $i < $links->length; $i++) { 		
		$link = $links->item($i); 
		$link = $link->getAttribute("href");
		$linkedPage = $webLink . $link;
		$response = file_get_contents($linkedPage);
			
		@$doc->loadHTML($response);		
		$xpath = new DOMXPath($doc);
				
		$namePattern = '/<h1>(.*?)<\/h1>/'; 
		preg_match($namePattern,$response,$name);
		$name = $name[1];

		$idPattern = '/_(.*?).html/';	
		preg_match($idPattern,$link,$id);
		$id = $id[1];
		
		$LogoUrl = $xpath->query("/html/body//img/@src");
		$LogoUrl = $LogoUrl->item(0);
		$LogoUrl= $LogoUrl ? $LogoUrl->nodeValue:'Bild saknas';  
		
		$addressPattern = '/Adress:(.*?)<br \/>/';	
		preg_match($addressPattern,$response,$address); 
		$address = $address[1];
		
		$zipCodePattern = '/Postnummer:(.*?)<br \/>/';
		preg_match($zipCodePattern,$response,$zipCode);
		$zipCode = $zipCode[1];
		
		$townPattern = '/Ort:(.*?)(<\/p>|\\n)/'; 
		preg_match($townPattern,$response,$town);
		$town = $town[1];
		
		$hemsida = $xpath->query("//p/a"); 
		$hemsida = $hemsida->item(0);
		$hemsida = $hemsida->nodeValue;
		
		$pages[] = array("id" => $id, "linkedPage" => $linkedPage, "hemsida" => $hemsida, "logoUrl" => $LogoUrl, "name" => $name, "adress" => $address, "zipCode" => $zipCode, "town" => $town);
		
	}
	 $xmldoc = new DOMDocument('1.0', 'UTF-8'); 
	 $xmldoc->formatOutput = true; 
	 $rootNode = $xmldoc->createElement("Handlare");
	 $rootNode = $xmldoc->appendChild($rootNode);
	 
	 foreach($pages as $key => $value){
	 $HandlareNr = $xmldoc->createElement("HandlareNr");
	 $HandlareNr = $rootNode->appendChild($HandlareNr);
	 	foreach($value as $key => $newValue){
	 		$node = $xmldoc->createElement($key, $newValue);
			$HandlareNr->appendChild($node);
	 	} 	
	 }

	 $xmldoc->saveXML();
	 $xmldoc->save("labb12.xml");
	 
	 $json = json_encode($pages);
	 $json = preg_replace_callback('/\\\\u([0-9a-f]{4})/i', 'replace_unicode_escape_sequence', $json); 
	 $jsonDoc = "C:/wamp/www/webbteknik2/jsondoc2.json";
	 $fh = fopen($jsonDoc, 'w');
	 fwrite($fh, $json);
	 fclose($fh);
	 
	 function replace_unicode_escape_sequence($match) {
    	return mb_convert_encoding(pack('H*', $match[1]), 'UTF-8', 'UCS-2BE');
	}

?>
