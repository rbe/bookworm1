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
import javax.persistence.Query;
import java.util.Date;

public class BkstpXmlRowProcessor extends AbstractXmlRowProcessor<Bestellkarte> {

    @Override
    public Bestellkarte xmlRowToEntity(final XmlRow xmlRow) {
        final Query findBookByTitelnummer = entityManager.createNamedQuery("Book.findByTitelnummer");
        final Bestellkarte bestellkarte = new Bestellkarte();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BKHNR":
                    bestellkarte.setHoerernummer(tagContent);
                    break;
                case "BKPDAT":
                    final Date datumStand = ParserHelper.parseIsoDate(tagContent);
                    if (null != datumStand) {
                        bestellkarte.setDatumStand(datumStand);
                    }
                    break;
                default:
                    final boolean startsWithBKP = xmlData.getTagName().startsWith("BKP");
                    if (startsWithBKP) {
                        /*
                        final String[] bkpSplit = xmlData.getTagName().split("BKP");
                        final int index = Integer.parseInt(bkpSplit[1]) - 1;
                        */
                        if (!tagContent.equals("0")) {
                            try {
                                final Book book = (Book) findBookByTitelnummer.setParameter("titelnummer", tagContent).getSingleResult();
                                bestellkarte.addBook(book);
                            } catch (NoResultException e) {
                                //LOGGER.warning(String.format("Book titelnummer=%s not found for XmlRow %s", tagContent, xmlRow));
                                // ignore
                            }
                        }
                    }
                    break;
            }
        }
        return bestellkarte;
    }

}
