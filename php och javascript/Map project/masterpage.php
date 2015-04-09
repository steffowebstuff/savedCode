<?php
class MasterPage{
		public function GetPage(){
			
		$html = "	
		<!DOCTYPE HTML SYSTEM>
			<head>
				<title>Index</title>
				<meta http-equiv='content-type' content='text/html; charset='utf-8''>	
				<link rel='stylesheet' type='text/css' href='labb3.css' /> 
				<script type='text/javascript' src='labb3.js'></script>			
			</head>
			<body>
				<div id='container'>
					<div id='menu'>
						<h3>Närproducerat</h3>
						<ul id='list'>
							<li>Hem</li>
							<li>Producenter</li>
							<li>Kontakt</li>
							<li>Om Projektet</li>	  
						</ul>
						<form name='input'  method='post'>
							<input type='text' name='Username' value='Användarnamn' /><br />
							<input type='text' name='Password' value='Lösenord' /><br />
							<input type='submit' name='LogoutButton' value='Logga in' />
						</form>
					</div>
					<div id='main'>
						<h1>Lorem Ipsum</h1>
						<img src='flowers.jpeg' />
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec erat nibh, fermentum tincidunt cursus eu, vestibulum dignissim tellus. Morbi pulvinar nisi sed nisi vehicula vitae condimentum dolor pellentesque. Nam vel ante tellus. Integer sollicitudin urna sit amet orci feugiat sit amet porta justo condimentum. Aliquam vel ligula ante. Praesent sed libero sit amet mi auctor hendrerit vitae non orci. Donec nisi libero, pretium vel tempus eu, scelerisque ornare eros. Maecenas eu arcu eu massa porttitor bibendum. Donec ut urna eros, blandit imperdiet felis. Nullam vel nibh sapien. Praesent nibh metus, aliquam ac semper quis, blandit et leo. Donec lacus nisi, eleifend in malesuada et, tincidunt nec turpis. Ut nec tellus id massa tristique dignissim</p>
						<br />
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec erat nibh, fermentum tincidunt cursus eu, vestibulum dignissim tellus. Morbi pulvinar nisi sed nisi vehicula vitae condimentum dolor pellentesque. Nam vel ante tellus. Integer sollicitudin urna sit amet orci feugiat sit amet porta justo condimentum. Aliquam vel ligula ante. Praesent sed libero sit amet mi auctor hendrerit vitae non orci. Donec nisi libero, pretium vel tempus eu, scelerisque ornare eros. Maecenas eu arcu eu massa porttitor bibendum. Donec ut urna eros, blandit imperdiet felis. Nullam vel nibh sapien. Praesent nibh metus, aliquam ac semper quis, blandit et leo. Donec lacus nisi, eleifend in malesuada et, tincidunt nec turpis. Ut nec tellus id massa tristique dignissim</p>				
					</div>
					<div id='producers>
						<h2>Ett urval av våra producenter</h2>
					</div>
					<div id='moreProducers'>
						<h2>Alla producenter</h2>
						<?php 
						echo 'testar php';
						$webLink = 'http://172.16.206.1/~thajo/1DV449/laboration01/producenter/';
						$response = file_get_contents('http://172.16.206.1/~thajo/1DV449/laboration01/producenter/producenter.html');
						$doc = new DOMDocument; 
						$doc->loadHTML($response);
						$xpath = new DOMXpath($doc); 	
						$links = $xpath->query('//table//a'); 
						$pages = array();
						?>
						<!--En dynamisk länklista som loopar runt och ta resten av producenterna -->
					</div>
					<p>åäö</p>
				</div>
			</body>
		";
		return $html;
	
	}
}

