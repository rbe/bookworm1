/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.hoererimport.HoererBuchHelper;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HoerstpImport {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static void hoerstpToSql() throws XMLStreamException {
        final XMLStreamReader reader = HoererBuchHelper.makeXMLStreamReaderFromResource("/HoererUndBuch/HOERSTP_Hoererstammdatei.xml");
        final OutputStream hoerstpOutput = HoererBuchHelper.makeSqlOutputStream("/HoererUndBuch", "HOERSTP_Hoererstammdatei.sql");
        Hoererstamm hoererstamm = null;
        int hoererCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        hoererstamm = new Hoererstamm();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            hoererCount++;
                            HoererBuchHelper.writeInsertStatement(hoerstpOutput, hoererstamm);
                            if (hoererCount % 100 == 0) {
                                System.out.println(hoererstamm);
                            }
                            break;
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
                        case "HOEPLZ":
                            hoererstamm.setPlz(tagContent);
                            break;
                        case "HOEORT":
                            hoererstamm.setOrt(tagContent);
                            break;
                        case "HOEGBD":
                            try {
                                final Date g = ISO_DATE_FORMAT.parse(tagContent);
                                hoererstamm.setGeburtsdatum(g);
                            } catch (ParseException e) {
                                // ignore
                            }
                            break;
                        case "HOETEL":
                            hoererstamm.setTelefonnummer(tagContent);
                            break;
                    }
                    break;
            }
        }
    }

}
