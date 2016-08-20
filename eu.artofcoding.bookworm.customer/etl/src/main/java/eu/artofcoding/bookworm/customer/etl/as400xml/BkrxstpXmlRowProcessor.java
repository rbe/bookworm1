/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.etl.as400xml;

import eu.artofcoding.bookworm.common.etl.xml.XmlData;
import eu.artofcoding.bookworm.common.etl.xml.XmlRow;
import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.customer.etl.xml.AbstractXmlRowProcessor;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

public class BkrxstpXmlRowProcessor extends AbstractXmlRowProcessor<BestellkarteArchiv> {

    private static final Logger LOGGER = Logger.getLogger(BkrxstpXmlRowProcessor.class.toString());

    @Override
    public BestellkarteArchiv xmlRowToEntity(final XmlRow xmlRow) {
        final TypedQuery<Book> findBookByTitelnummer = entityManager.createNamedQuery("Book.findByTitelnummer", Book.class);
        final BestellkarteArchiv bestellkarteArchiv = new BestellkarteArchiv();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BEXLNR":
                    bestellkarteArchiv.setHoerernummer(tagContent);
                    break;
                case "BEXTIT":
                    final List<Book> books = findBookByTitelnummer.setParameter("titelnummer", tagContent).getResultList();
                    if (null != books && books.size() == 1) {
                        bestellkarteArchiv.setBuch(books.get(0));
                    } else {
                        LOGGER.warning("Book " + tagContent + " not found");
                    }
                    break;
                case "BEXDAT":
                    bestellkarteArchiv.setAusleihdatum(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "BEXKZ":
                    bestellkarteArchiv.setKennzeichen(tagContent);
                    break;
            }
        }
        return bestellkarteArchiv;
    }

}
