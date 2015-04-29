/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.etl;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.etl.CamelFileProcessor;
import eu.artofcoding.bookworm.common.etl.helper.XmlStreamHelper;
import eu.artofcoding.bookworm.common.etl.xml.XmlRowProcessor;
import eu.artofcoding.bookworm.customer.etl.xml.XmlRowParser;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.XMLStreamReader;
import java.util.List;

public class HoererImportProcessor implements CamelFileProcessor {

    private XmlRowProcessor xmlRowProcessor;

    public void setXmlRowProcessor(XmlRowProcessor xmlRowProcessor) {
        this.xmlRowProcessor = xmlRowProcessor;
    }

    @Transactional
    public List<GenericEntity> importFile(final String body) throws Exception {
        // Check state
        if (null == xmlRowProcessor) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Insert data
        final XMLStreamReader reader = XmlStreamHelper.makeXMLStreamReaderFromResource(body);
        final List<GenericEntity> genericEntities = new XmlRowParser(xmlRowProcessor).xmlRowsToEntities(reader);
        return genericEntities;
    }

}
