/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.AktuelleBestellkarte;
import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;
import eu.artofcoding.bookworm.api.helper.SqlStatement;

import java.util.Date;

public class BkstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final AktuelleBestellkarte aktuelleBestellkarte = new AktuelleBestellkarte();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BKHNR":
                    aktuelleBestellkarte.setHoerernummer(tagContent);
                    break;
                case "BKPDAT":
                    final Date datumStand = SqlStatement.parseIsoDate(tagContent);
                    if (null == datumStand) {
                        LOGGER.warning("Could not parse date " + tagContent + " for XmlRow " + xmlRow);
                    } else {
                        aktuelleBestellkarte.setDatumStand(datumStand);
                    }
                    break;
                default:
                    final boolean startsWithBKP = xmlData.getTagName().startsWith("BKP");
                    if (startsWithBKP) {
                        final String[] bkpSplit = xmlData.getTagName().split("BKP");
                        final int index = new Integer(bkpSplit[1]).intValue() - 1;
                        if (!tagContent.equals("0")) {
                            aktuelleBestellkarte.addTitelnummer(index, tagContent);
                        }
                    }
                    break;
            }
        }
        validateAndMerge(aktuelleBestellkarte);
    }

}
