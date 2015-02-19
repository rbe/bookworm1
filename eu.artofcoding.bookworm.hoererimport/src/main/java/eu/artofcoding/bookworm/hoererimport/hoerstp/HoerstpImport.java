/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.hoerstp;

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

public class HoerstpImport {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    private static final XMLInputFactory factory = XMLInputFactory.newInstance();

    public static void hoerstpToSql() throws XMLStreamException, URISyntaxException, IOException {
        final InputStream hoerstpInput = HoererBuchImport.class.getResourceAsStream("/HoererUndBuch/HOERSTP_Hoererstammdatei.xml");
        final XMLStreamReader reader = factory.createXMLStreamReader(hoerstpInput);
        final URL resource = HoererBuchImport.class.getResource("/HoererUndBuch");
        final Path outputDirectory = Paths.get(resource.toURI());
        final Path outputPath = Paths.get(outputDirectory.toString(), "HOERSTP_Hoererstammdatei.sql");
        final OutputStream hoerstpOutput = Files.newOutputStream(outputPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
                            hoerstpOutput.write(hoererstamm.toString().getBytes());
                            hoerstpOutput.write("\n".getBytes());
                            if (hoererCount % 100 == 0) {
                                System.out.println(hoererstamm);
                            }
                            break;
                        case "HOENR":
                            hoererstamm.hoerernummer = tagContent;
                            break;
                        case "HOEAN":
                            hoererstamm.anrede = tagContent;
                            break;
                        case "HOEVN":
                            hoererstamm.vorname = tagContent;
                            break;
                        case "HOENN":
                            hoererstamm.nachname = tagContent;
                            break;
                        case "HOEN2":
                            hoererstamm.name2 = tagContent;
                            break;
                        case "HOESTR":
                            hoererstamm.strasse = tagContent;
                            break;
                        case "HOEPLZ":
                            hoererstamm.plz = tagContent;
                            break;
                        case "HOEORT":
                            hoererstamm.ort = tagContent;
                            break;
                        case "HOEGBD":
                            try {
                                final Date g = simpleDateFormat.parse(tagContent);
                                hoererstamm.geburtsdatum = g;
                            } catch (ParseException e) {
                                // ignore
                            }
                            break;
                        case "HOETEL":
                            hoererstamm.telefonnummer = tagContent;
                            break;
                    }
                    break;
                /*
                case XMLStreamConstants.START_DOCUMENT:
                    hoererList = new ArrayList<>();
                    break;
                */
            }
        }
    }

}
