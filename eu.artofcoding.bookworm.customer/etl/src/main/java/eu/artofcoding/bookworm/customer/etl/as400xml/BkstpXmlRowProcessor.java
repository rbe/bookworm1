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
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.customer.etl.xml.AbstractXmlRowProcessor;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;

public class BkstpXmlRowProcessor extends AbstractXmlRowProcessor<Bestellkarte> {

    private void parseBkpdat(final Bestellkarte bestellkarte, final String tagContent) {
        final Date datumStand = ParserHelper.parseIsoDate(tagContent);
        if (null != datumStand) {
            bestellkarte.setDatumStand(datumStand);
        }
    }

    private void parseBkp(final Bestellkarte bestellkarte, final XmlData xmlData, final String tagContent) {
        final TypedQuery<Book> findBookByTitelnummer = entityManager.createNamedQuery("Book.findByTitelnummer", Book.class);
        final boolean startsWithBKP = xmlData.getTagName().startsWith("BKP");
        if (startsWithBKP && !"0".equals(tagContent)) {
            try {
                final Book book = findBookByTitelnummer.setParameter("titelnummer", tagContent).getSingleResult();
                bestellkarte.addBook(book);
            } catch (NoResultException e) {
                // ignore
            }
        }
    }

    @Override
    public Bestellkarte xmlRowToEntity(final XmlRow xmlRow) {
        final Bestellkarte bestellkarte = new Bestellkarte();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BKHNR":
                    bestellkarte.setHoerernummer(tagContent);
                    break;
                case "BKPDAT":
                    parseBkpdat(bestellkarte, tagContent);
                    break;
                default:
                    parseBkp(bestellkarte, xmlData, tagContent);
                    break;
            }
        }
        return bestellkarte;
    }

}
