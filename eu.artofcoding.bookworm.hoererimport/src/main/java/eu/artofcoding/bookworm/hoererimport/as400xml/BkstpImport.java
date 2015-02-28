/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.hoerer.AktuelleBestellkarte;
import eu.artofcoding.bookworm.hoererimport.HoererBuchHelper;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BkstpImport {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static void bkstpToSql() throws XMLStreamException {
        final XMLStreamReader reader = HoererBuchHelper.makeXMLStreamReaderFromResource("/HoererUndBuch/BKSTP_Bestellkarten_StD.xml");
        final OutputStream hoerstpOutput = HoererBuchHelper.makeSqlOutputStream("/HoererUndBuch", "BKSTP_Bestellkarten_StD.sql");
        AktuelleBestellkarte aktuelleBestellkarte = null;
        int archivCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        aktuelleBestellkarte = new AktuelleBestellkarte();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            archivCount++;
                            HoererBuchHelper.writeInsertStatement(hoerstpOutput, aktuelleBestellkarte);
                            if (archivCount % 100 == 0) {
                                System.out.println(aktuelleBestellkarte);
                            }
                            break;
                        case "BKHNR":
                            aktuelleBestellkarte.setHoerernummer(tagContent);
                            break;
                        case "BKPDAT":
                            try {
                                final Date s = ISO_DATE_FORMAT.parse(tagContent);
                                aktuelleBestellkarte.setDatumStand(s);
                            } catch (ParseException e) {
                                // ignore
                            }
                            break;
                        default:
                            final boolean startsWithBKP = reader.getLocalName().startsWith("BKP");
                            if (startsWithBKP) {
                                final String[] bkpSplit = reader.getLocalName().split("BKP");
                                final int index = new Integer(bkpSplit[1]).intValue() - 1;
                                if (!tagContent.equals("0")) {
                                    aktuelleBestellkarte.addTitelnummer(index, tagContent);
                                }
                            }
                            break;
                    }
                    break;
            }
        }
    }

}
