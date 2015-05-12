<?php

class MasterPage{
	
	public function __construct($charset = "utf-8") {
    $this->m_charset = $charset;
  	}
	private $m_charset;
  //Layoutmallen som används
	public function GetVestaliaPage($title,$body, $links) { 
		$html = "        
			<!DOCTYPE HTML SYSTEM>
				<head>
					<title>$title</title>
					<meta http-equiv='content-type' content='text/html; charset=$this->m_charset'>	
					<link rel='stylesheet' type='text/css' href='css/vestalia2.css' />
					<style type='text/css' media='print'> #container {visibility:hidden;} p, h1, h2 {visibility:visible;} </style> 
						<script type='text/javascript'>
						  var _gaq = _gaq || [];
						  _gaq.push(['_setAccount', 'UA-26580332-1']);
						  _gaq.push(['_trackPageview']);
						
						  (function() {
							var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
							ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
							var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
						  })();
					</script>
				</head>
				<body>
					<div id='container'>
						<div id='logotype'>
							<img src='vestalialogga.png' alt='vestalias logotype' />
						</div>
						<div id='nav'>
							<ul>
								<li><a href='index.php'>Hem</a></li>
								<li><a href='bakgrund.html'Bakgrund</a></li>
								<li><a href='tjanster.html'>Tjänster</a></li>
								<li><a href='kontakt.html'>Kontakt</a></li>
								<li><a href='synpunkter.html'>Synpunkter</a></li>
							</ul>
						</div>
						<div id='content'>
							<div id='maincontent'>
								<div id='above'>
									<img src='reception.png' alt='bild på reception' />
									<img src='pil2.png' alt='bild på pil' />
									<img src='port2.png' alt='bild på port' />
							</div>
						  <div id='below'>
								<h1> Projektsida</h1>
								$body
								<li><a href='index.php'>Tillbaka till inloggning</a></li>
							</div>
						</div><!--maincontent-->
							
						<div id='sidebar'>
							<h2>$links</h2>
			
						</div>
					</div><!--content-->
						
					<div id='footer'> 
						<p>&copy;Vestalia AB 2010</p>
					</div>
				</div><!--container-->	
			 </body>
		</html>";		
		return $html;
	}
}
