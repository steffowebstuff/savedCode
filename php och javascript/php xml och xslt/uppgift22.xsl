<?xml version= "1.0" encoding="utf-8"?>
<!--Xsl fil som ska hitta koordinaterna även från den här filen http://172.16.206.1/~thajo/1DV449/laboration01/producenter/positioner.xml-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">	
	<xsl:variable name="document" select="document('positioner2.xml')" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Uppgift2 Labb1</title>
			</head>
			<body>
				<table border="1">
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Adress</th>
						<th>Postnummer</th>
						<th>Ort</th>																		
						<th>Logo</th>
						<th>Hemsida</th>
						<th>Latitude</th>
						<th>Longitude</th>
					</tr>
					<xsl:for-each select="//HandlareNr">
					<xsl:variable name="id" select="./id" />
						<tr>
							<td><xsl:value-of select="./id"/></td>					
							<td><xsl:value-of select="./name"/></td>
							<td><xsl:value-of select="./adress" /></td>
							<td><xsl:value-of select="./zipCode"/></td>
							<td><xsl:value-of select="./town"/></td>
							<td><xsl:value-of select="./logoUrl" /></td>																					
							<td><xsl:value-of select="./hemsida"/></td>							
							<xsl:for-each select="$document//ss:Table//ss:Row">
								<xsl:if test="ss:Cell//ss:Data = $id">
									<td><xsl:value-of select="ss:Cell[position() = 2]/ss:Data" /></td>
									<td><xsl:value-of select="ss:Cell[position() = 3]/ss:Data" /></td>
								</xsl:if>
							</xsl:for-each>			
						</tr>
				</xsl:for-each>				
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>