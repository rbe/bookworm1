/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.hoekz;

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

public class HoekzstpImport {

    private static final XMLInputFactory factory = XMLInputFactory.newInstance();

    public static void hoekzstpToSql() throws XMLStreamException, URISyntaxException, IOException {
        final InputStream hoerstpInput = HoererBuchImport.class.getResourceAsStream("/HoererUndBuch/HOEKZSTP_Hoerer_Kennzeichen.xml");
        final XMLStreamReader reader = factory.createXMLStreamReader(hoerstpInput);
        final URL resource = HoererBuchImport.class.getResource("/HoererUndBuch");
        final Path outputDirectory = Paths.get(resource.toURI());
        final Path outputPath = Paths.get(outputDirectory.toString(), "HOEKZSTP_Hoerer_Kennzeichen.sql");
        final OutputStream hoerstpOutput = Files.newOutputStream(outputPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        HoererKz hoererKz = null;
        int hoererCount = 0;
        String tagContent = null;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        hoererKz = new HoererKz();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "row":
                            hoererCount++;
                            hoerstpOutput.write(hoererKz.toString().getBytes());
                            hoerstpOutput.write("\n".getBytes());
                            if (hoererCount % 100 == 0) {
                                System.out.println(hoererKz);
                            }
                            break;
                        case "HOEKZN":
                            hoererKz.hoerernummer = tagContent;
                            break;
                        case "HOKZ12":
                            hoererKz.email = tagContent;
                            break;
                    }
                    break;
            }
        }
    }

}
