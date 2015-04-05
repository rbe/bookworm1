/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport;

import eu.artofcoding.bookworm.common.etl.CamelFileProcessor;
import eu.artofcoding.bookworm.common.etl.helper.XmlStreamHelper;
import eu.artofcoding.bookworm.common.etl.xml.XmlRowProcessor;
import eu.artofcoding.bookworm.customer.hoererimport.as400xml.XmlRowParser;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.XMLStreamReader;

public class HoererImportProcessor implements CamelFileProcessor {

    private XmlRowProcessor xmlRowProcessor;

    public void setXmlRowProcessor(XmlRowProcessor xmlRowProcessor) {
        this.xmlRowProcessor = xmlRowProcessor;
    }

    @Transactional
    public void importFile(final String body) throws Exception {
        // Check state
        if (null == xmlRowProcessor) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Insert data
        final XMLStreamReader reader = XmlStreamHelper.makeXMLStreamReaderFromResource(body);
        new XmlRowParser(xmlRowProcessor).processXml(reader);
    }

}
