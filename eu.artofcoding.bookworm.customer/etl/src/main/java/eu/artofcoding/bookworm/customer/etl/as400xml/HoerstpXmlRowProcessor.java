/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.etl.as400xml;

import eu.artofcoding.bookworm.common.etl.xml.XmlData;
import eu.artofcoding.bookworm.common.etl.xml.XmlRow;
import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.etl.xml.AbstractXmlRowProcessor;

public class HoerstpXmlRowProcessor extends AbstractXmlRowProcessor<Hoererstamm> {

    @Override
    public Hoererstamm xmlRowToEntity(final XmlRow xmlRow) {
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
                case "HOETV":
                    hoererstamm.setSperrTerminVon(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "HOETB":
                    hoererstamm.setSperrTerminBis(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "HOEUV":
                    hoererstamm.setUrlaubVon(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "HOEUB":
                    hoererstamm.setUrlaubBis(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "HOEGBD":
                    hoererstamm.setGeburtsdatum(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "HOETEL":
                    hoererstamm.setTelefonnummer(tagContent);
                    break;
            }
        }
        return hoererstamm;
    }

}
