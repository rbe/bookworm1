/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;
import eu.artofcoding.bookworm.api.helper.SqlStatement;

public class BkrxstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final BestellkarteArchiv bestellkarteArchiv = new BestellkarteArchiv();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BEXLNR":
                    bestellkarteArchiv.setHoerernummer(tagContent);
                    break;
                case "BEXTIT":
                    bestellkarteArchiv.setTitelnummer(tagContent);
                    break;
                case "BEXDAT":
                    bestellkarteArchiv.setAusleihdatum(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "BEXKZ":
                    bestellkarteArchiv.setKennzeichen(tagContent);
                    break;
            }
        }
        validateAndMerge(bestellkarteArchiv);
    }

}