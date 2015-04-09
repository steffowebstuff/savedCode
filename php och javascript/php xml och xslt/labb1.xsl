<?xml version= "1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>Uppgift2 Labb1</title>
			</head>
			<body>
				<table border="1">
					<tr>
						<th>Name</th>
						<th>LogoAdress</th>
						<th>Postnummer</th>
						<th>Adress</th>
						<th>Ort</th>
						<th>Id</th>
						<th>Hemsida</th>
						<th>Latitude</th>
						<th>Longitude</th>
					</tr>
					<xsl:for-each select="Handlare" >
					<tr>					
						<td><xsl:value-of select="//name"/></td>
						<td><xsl:value-of select="//logoUrl" /></td>
						<td><xsl:value-of select="//adress" /></td>
						<td><xsl:value-of select="//zipCode"/></td>
						<td><xsl:value-of select="//town"/></td>
						<td><xsl:value-of select="//id"/></td>
						<td><xsl:value-of select="//hemsida"/></td>
						<td><xsl:value-of select="//latitude"/></td>
						<td><xsl:value-of select="//longitude"/></td>
					</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>