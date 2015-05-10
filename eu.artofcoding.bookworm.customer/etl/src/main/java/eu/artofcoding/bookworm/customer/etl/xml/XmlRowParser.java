/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.etl.xml;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.etl.xml.XmlData;
import eu.artofcoding.bookworm.common.etl.xml.XmlRow;
import eu.artofcoding.bookworm.common.etl.xml.XmlRowProcessor;

import javax.persistence.NoResultException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;
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

    private XmlData convertToXmlData(final XMLStreamReader reader, final String localName) throws XMLStreamException {
        final XmlData xmlData = new XmlData();
        xmlData.setTagName(localName);
        final StringBuilder tagContent = new StringBuilder();
        int readerEventType = reader.getEventType();
        while (XMLStreamConstants.CHARACTERS == readerEventType) {
            final String str = reader.getText().trim();
            tagContent.append(str);
            readerEventType = reader.next();
        }
        xmlData.setTagContent(tagContent.toString());
        final boolean endFound = XMLStreamConstants.END_ELEMENT == readerEventType;
        if (endFound) {
            return xmlData;
        } else {
            throw throwUnexpectedEvent();
        }
    }

    private XmlRow convertToXmlRow(final XMLStreamReader reader) {
        final XmlRow xmlRow = new XmlRow();
        try {
            String localName = null;
            while (reader.hasNext()) {
                final int eventWithinRow = reader.next();
                final boolean startElement = XMLStreamConstants.START_ELEMENT == eventWithinRow;
                final boolean endElement = XMLStreamConstants.END_ELEMENT == eventWithinRow;
                final boolean endOfRow = endElement && reader.getLocalName().equals("row");
                final boolean hasData = XMLStreamConstants.CHARACTERS == eventWithinRow && reader.getText().trim().length() > 0;
                if (startElement) {
                    localName = reader.getLocalName();
                } else if (hasData) {
                    final XmlData xmlData = convertToXmlData(reader, localName);
                    xmlRow.addXmlData(xmlData);
                } else if (endOfRow) {
                    return xmlRow;
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public List<GenericEntity> xmlRowsToEntities(final XMLStreamReader reader) {
        List<GenericEntity> genericEntities = new ArrayList<>();
        try {
            while (reader.hasNext()) {
                final int event = reader.next();
                final boolean startOfRow = XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals("row");
                if (startOfRow) {
                    final XmlRow xmlRow = convertToXmlRow(reader);
                    try {
                        final GenericEntity entity = xmlRowProcessor.xmlRowToEntity(xmlRow);
                        genericEntities.add(entity);
                    } catch (NoResultException e) {
                        LOGGER.fine(String.format("Cannot process row %s: No result from database, %s", xmlRow, e.getMessage()));
                    } catch (Exception e) {
                        LOGGER.severe(String.format("Cannot process row %s: %s", xmlRow, e.getMessage()));
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return genericEntities;
    }

}
