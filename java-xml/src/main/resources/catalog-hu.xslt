<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
	<konyvtar xmlns:kv="http://training360.com/ns/konyvtar">
    <xsl:for-each select="/catalog/book">
    <kv:konyv>
      <kv:cim><xsl:value-of select="title"/></kv:cim>
      <xsl:if test="available"><elerheto /></xsl:if>
    </kv:konyv>
    </xsl:for-each>
</konyvtar>
</xsl:template>
</xsl:stylesheet>