/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;

public class HoekzstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final HoererKennzeichen hoererKennzeichen = new HoererKennzeichen();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "HOEKZN":
                    hoererKennzeichen.setHoerernummer(tagContent);
                    break;
                case "HOKZ12":
                    hoererKennzeichen.setEmail(tagContent);
                    break;
            }
        }
        validateAndMerge(hoererKennzeichen);
    }

}
