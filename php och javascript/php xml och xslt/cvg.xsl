<?xml version="1.0" encoding="utf-8"?>
//S� h�r g�r man f�r att skriva en svg-fil. 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method=�xml� indent=�yes�/>
	<xsl:param name="parameter" />
	<xsl:template match="/">
		
		<html>
			<head>
				<title>SVG Test</title>
			</head>
			<xsl:variable name="shape" /> //Med �ven en select variabel h�r i. 
			<body>
				<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
					<circle cx="parameter" /> //Plus lite �vriga attribut.
				</svg>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
