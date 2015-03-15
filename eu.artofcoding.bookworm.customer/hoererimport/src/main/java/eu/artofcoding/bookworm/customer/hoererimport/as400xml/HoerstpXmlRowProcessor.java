/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;
import eu.artofcoding.bookworm.api.helper.SqlStatement;

public class HoerstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final Hoererstamm hoererstamm = new Hoererstamm();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "HOENR":
                    hoererstamm.setHoerernummer(tagContent);
                    break;
                case "HOEAN":
                    hoererstamm.setAnrede(tagContent);
                    break;
                case "HOEVN":
                    hoererstamm.setVorname(tagContent);
                    break;
                case "HOENN":
                    hoererstamm.setNachname(tagContent);
                    break;
                case "HOEN2":
                    hoererstamm.setName2(tagContent);
                    break;
                case "HOESTR":
                    hoererstamm.setStrasse(tagContent);
                    break;
                case "HOEPLL":
                    hoererstamm.setLand(tagContent);
                    break;
                case "HOEPLZ":
                    hoererstamm.setPlz(tagContent);
                    break;
                case "HOEORT":
                    hoererstamm.setOrt(tagContent);
                    break;
                case "HOUKZ":
                    hoererstamm.setUrlaubKennzeichen(tagContent);
                    break;
                case "HOUN2":
                    hoererstamm.setUrlaubName2(tagContent);
                    break;
                case "HOUSTR":
                    hoererstamm.setUrlaubStrasse(tagContent);
                    break;
                case "HOUPLL":
                    hoererstamm.setUrlaubLand(tagContent);
                    break;
                case "HOUPLZ":
                    hoererstamm.setUrlaubPlz(tagContent);
                    break;
                case "HOUORT":
                    hoererstamm.setUrlaubOrt(tagContent);
                    break;
                case "HOERV":
                    hoererstamm.setUrlaubVon(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "HOERB":
                    hoererstamm.setUrlaubBis(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "HOEGBD":
                    hoererstamm.setGeburtsdatum(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "HOETEL":
                    hoererstamm.setTelefonnummer(tagContent);
                    break;
            }
        }
        validateAndMerge(hoererstamm);
    }

}
