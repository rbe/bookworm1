/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.hoererimport.HoererBuchHelper;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.OutputStream;

public class HoekzstpImport {

    public static void hoekzstpToSql() throws XMLStreamException {
        final XMLStreamReader reader = HoererBuchHelper.makeXMLStreamReaderFromResource("/HoererUndBuch/HOEKZSTP_Hoerer_Kennzeichen.xml");
        final OutputStream hoerstpOutput = HoererBuchHelper.makeSqlOutputStream("/HoererUndBuch", "HOEKZSTP_Hoerer_Kennzeichen.sql");
        HoererKennzeichen hoererKennzeichen = null;
        int hoererCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        hoererKennzeichen = new HoererKennzeichen();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            hoererCount++;
                            HoererBuchHelper.writeInsertStatement(hoerstpOutput, hoererKennzeichen);
                            if (hoererCount % 100 == 0) {
                                System.out.println(hoererKennzeichen);
                            }
                            break;
                        case "HOEKZN":
                            hoererKennzeichen.setHoerernummer(tagContent);
                            break;
                        case "HOKZ12":
                            hoererKennzeichen.setEmail(tagContent);
                            break;
                    }
                    break;
            }
        }
    }

}
