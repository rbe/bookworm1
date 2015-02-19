/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.bkp;

import eu.artofcoding.bookworm.hoererimport.HoererBuchImport;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BkstpImport {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    private static final XMLInputFactory factory = XMLInputFactory.newInstance();

    public static void bkstpToSql() throws XMLStreamException, URISyntaxException, IOException {
        final InputStream hoerstpInput = HoererBuchImport.class.getResourceAsStream("/HoererUndBuch/BKSTP_Bestellkarten_StD.xml");
        final XMLStreamReader reader = factory.createXMLStreamReader(hoerstpInput);
        final URL resource = HoererBuchImport.class.getResource("/HoererUndBuch");
        final Path outputDirectory = Paths.get(resource.toURI());
        final Path outputPath = Paths.get(outputDirectory.toString(), "BKSTP_Bestellkarten_StD.sql");
        final OutputStream hoerstpOutput = Files.newOutputStream(outputPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        HoererstammAktuelleBestellkarte archiv = null;
        int archivCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        archiv = new HoererstammAktuelleBestellkarte();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            archivCount++;
                            hoerstpOutput.write(archiv.toString().getBytes());
                            hoerstpOutput.write("\n".getBytes());
                            if (archivCount % 100 == 0) {
                                System.out.println(archiv);
                            }
                            break;
                        case "BKHNR":
                            archiv.hoerernummer = tagContent;
                            break;
                        case "BKPDAT":
                            try {
                                final Date s = simpleDateFormat.parse(tagContent);
                                archiv.datumStand = s;
                            } catch (ParseException e) {
                                // ignore
                            }
                            break;
                        default:
                            if (reader.getLocalName().startsWith("BKP")) {
                                final String[] bkpSplit = reader.getLocalName().split("BKP");
                                final int index = new Integer(bkpSplit[1]).intValue() - 1;
                                if (!tagContent.equals("0")) {
                                    archiv.titelnummern[index] = tagContent;
                                }
                            }
                            break;
                    }
                    break;
            }
        }
    }

}
