<?xml version= "1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="filetype"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>Uppgift3 Labb1</title>
			</head>
			<body>				
				<xsl:if test="$filetype = 'xhtml'">
						<xsl:call-template name="runXhtml" />						
				</xsl:if>
				
				<xsl:if test="$filetype = 'svg'">
					<xsl:call-template name="runSvg" />
				</xsl:if>
					
				<xsl:if test="$filetype = 'xhtml_svg'">
					<xsl:call-template name="runXhtml" />
					<xsl:call-template name="runSvg" />
				</xsl:if>				
			</body>
		</html>
	</xsl:template>
	<xsl:template name="runXhtml">
					<table border="1"> 
						<tr>
							<th>Country</th>
							<th>Name</th>
							<th>Silver</th>
							<th>Bronze</th>
						</tr>
						<xsl:for-each select="//country">
							<xsl:sort order="descending" data-type="number" select="./gold"/>
							<tr>
								<td><xsl:value-of select="./@name"/></td>
								<td><xsl:value-of select="./gold"/></td>
								<td><xsl:value-of select="./silver"/></td>
								<td><xsl:value-of select="./bronze"/></td>
							</tr>
						</xsl:for-each>
					</table>
	</xsl:template>
	<xsl:template name="runSvg">
					<h1>Olympic 2006 <xsl:value-of select="//location/country"/>, <xsl:value-of select="//location/city"/></h1>
					<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="400" height="400">
						<xsl:for-each select="//country">
							<xsl:sort order="descending" data-type="number" select="./gold"/>
							<xsl:variable name="nrGold" select="./gold" />
							<rect x="90" y="{position()*25-25}" height="18" width="{$nrGold*10}" fill="yellow" />
							<text x="{position()+10}" y= "{position()*25-10}"><xsl:value-of select="./@name"/></text>
							<text x="{$nrGold*10.5 + 95}" y= "{position()*25-10}"><xsl:value-of select="./gold"/></text>											
						</xsl:for-each>
					</svg>
  	</xsl:template>
</xsl:stylesheet>