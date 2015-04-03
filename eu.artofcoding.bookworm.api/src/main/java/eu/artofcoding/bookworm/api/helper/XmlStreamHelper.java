/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.helper;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class XmlStreamHelper {

    private static final XMLInputFactory factory = XMLInputFactory.newInstance();

    private XmlStreamHelper() {
        throw new AssertionError();
    }

    public static XMLStreamReader makeXMLStreamReaderFromResource(final File xmlFile) {
        try {
            final InputStream inputStream = Files.newInputStream(xmlFile.toPath(), StandardOpenOption.READ);
            return factory.createXMLStreamReader(inputStream);
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    public static XMLStreamReader makeXMLStreamReaderFromResource(final Path xmlPath) {
        return makeXMLStreamReaderFromResource(xmlPath.toFile());
    }

    public static XMLStreamReader makeXMLStreamReaderFromResource(final String xml) {
        try {
            return factory.createXMLStreamReader(new StringReader(xml));
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

}
