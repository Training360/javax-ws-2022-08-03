<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<konyvtar>
    <xsl:for-each select="/catalog/book">
    <konyv>
      <cim><xsl:value-of select="title"/></cim>
      <xsl:if test="available"><elerheto /></xsl:if>
    </konyv>
    </xsl:for-each>
</konyvtar>
</xsl:template>
</xsl:stylesheet>