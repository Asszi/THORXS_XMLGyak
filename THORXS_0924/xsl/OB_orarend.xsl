<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
        <html>
            <body>
                <center>
                    <h1>Orarend</h1>
                    <table border="1 solid">
                        <tr bgcolor="cyan">
                            <th>Targy</th>
                            <th>Nap</th>
                            <th>Tol</th>
                            <th>Ig</th>
                            <th>Helyszin</th>
                            <th>Oktato></th>
                            <th>Szak</th>
                        </tr>
                        <xsl:for-each select="orarend/ora">
                            <tr>
                                <td><xsl:value-of select="targy"></xsl:value-of></td>
                                <td><xsl:value-of select="idopont/nap"></xsl:value-of></td>
                                <td><xsl:value-of select="idopont/tol"></xsl:value-of></td>
                                <td><xsl:value-of select="idopont/ig"></xsl:value-of></td>
                                <td><xsl:value-of select="helyszin"></xsl:value-of></td>
                                <td><xsl:value-of select="oktato"></xsl:value-of></td>
                                <td><xsl:value-of select="szak"></xsl:value-of></td>
                            </tr>
                        </xsl:for-each>
                    </table>
                    <b>Orehovszki Balazs</b>
                </center>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>