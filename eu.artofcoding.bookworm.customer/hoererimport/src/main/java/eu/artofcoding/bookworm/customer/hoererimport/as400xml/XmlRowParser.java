/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;
import eu.artofcoding.bookworm.api.xml.XmlRowProcessor;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.logging.Logger;

public final class XmlRowParser {

    private static final Logger LOGGER = Logger.getLogger(XmlRowParser.class.toString());

    private final XmlRowProcessor xmlRowProcessor;

    public XmlRowParser(final XmlRowProcessor xmlRowProcessor) {
        this.xmlRowProcessor = xmlRowProcessor;
    }

    private IllegalStateException throwUnexpectedEvent() {
        return new IllegalStateException("Unexpected event");
    }

    private XmlData processColumn(final XMLStreamReader reader, final String localName) throws XMLStreamException {
        final XmlData xmlData = new XmlData();
        xmlData.setTagName(localName);
        final boolean bodyFound = XMLStreamConstants.CHARACTERS == reader.getEventType();
        if (bodyFound) {
            final String tagContent = reader.getText().trim();
            xmlData.setTagContent(tagContent);
        } else {
            throw throwUnexpectedEvent();
        }
        final int eventWithinElement = reader.next();
        final boolean endFound = XMLStreamConstants.END_ELEMENT == eventWithinElement;
        if (endFound) {
            return xmlData;
        } else {
            throw throwUnexpectedEvent();
        }
    }

    private XmlRow processRow(final XMLStreamReader reader) {
        final XmlRow xmlRow = new XmlRow();
        try {
            String localName = null;
            while (reader.hasNext()) {
                final int eventWithinRow = reader.next();
                final boolean startElement = XMLStreamConstants.START_ELEMENT == eventWithinRow;
                final boolean endElement = XMLStreamConstants.END_ELEMENT == eventWithinRow;
                if (startElement) {
                    localName = reader.getLocalName();
                } else if (endElement && reader.getLocalName().equals("row")) {
                    return xmlRow;
                } else if (XMLStreamConstants.CHARACTERS == eventWithinRow && reader.getText().trim().length() > 0) {
                    final XmlData xmlData = processColumn(reader, localName);
                    xmlRow.addXmlData(xmlData);
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public void processXml(final XMLStreamReader reader) {
        try {
            while (reader.hasNext()) {
                final int event = reader.next();
                final boolean startElement = XMLStreamConstants.START_ELEMENT == event;
                if (startElement && reader.getLocalName().equals("row")) {
                    final XmlRow xmlRow = processRow(reader);
                    try {
                        xmlRowProcessor.xmlRowToEntity(xmlRow);
                    } catch (Exception e) {
                        LOGGER.warning(String.format("Cannot process row %s: %s", xmlRow, e.getMessage()));
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

}
