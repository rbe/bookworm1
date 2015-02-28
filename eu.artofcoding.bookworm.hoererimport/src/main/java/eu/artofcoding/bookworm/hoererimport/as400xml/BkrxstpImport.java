/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.hoererimport.HoererBuchHelper;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BkrxstpImport {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static void bkrxstpToSql() throws XMLStreamException {
        final XMLStreamReader reader = HoererBuchHelper.makeXMLStreamReaderFromResource("/HoererUndBuch/BKRXSTP_Archiv.xml");
        final OutputStream bkrxstpOutput = HoererBuchHelper.makeSqlOutputStream("/HoererUndBuch", "BKRXSTP.sql");
        BestellkarteArchiv bestellkarteArchiv = null;
        int archivCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        bestellkarteArchiv = new BestellkarteArchiv();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            archivCount++;
                            HoererBuchHelper.writeInsertStatement(bkrxstpOutput, bestellkarteArchiv);
                            if (archivCount % 100 == 0) {
                                System.out.println(bestellkarteArchiv);
                            }
                            break;
                        case "BKHNR":
                            bestellkarteArchiv.setHoerernummer(tagContent);
                            break;
                        case "BKPDAT":
                            try {
                                final Date s = ISO_DATE_FORMAT.parse(tagContent);
                                bestellkarteArchiv.setAusleihdatum(s);
                            } catch (ParseException e) {
                                // ignore
                            }
                            break;
                        case "BEXTIT":
                            bestellkarteArchiv.setTitelnummer(tagContent);
                            break;
                    }
                    break;
            }
        }
    }

}
