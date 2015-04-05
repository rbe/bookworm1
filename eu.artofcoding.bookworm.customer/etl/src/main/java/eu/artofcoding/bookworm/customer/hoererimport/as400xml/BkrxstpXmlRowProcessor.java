/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.common.etl.xml.XmlData;
import eu.artofcoding.bookworm.common.etl.xml.XmlRow;
import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;

import javax.persistence.Query;

public class BkrxstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final Query findBookByTitelnummer = entityManager.createNamedQuery("Book.findByTitelnummer");
        final BestellkarteArchiv bestellkarteArchiv = new BestellkarteArchiv();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BEXLNR":
                    bestellkarteArchiv.setHoerernummer(tagContent);
                    break;
                case "BEXTIT":
                    final Book book = (Book) findBookByTitelnummer.setParameter("titelnummer", tagContent).getSingleResult();
                    bestellkarteArchiv.setBuch(book);
                    break;
                case "BEXDAT":
                    bestellkarteArchiv.setAusleihdatum(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "BEXKZ":
                    bestellkarteArchiv.setKennzeichen(tagContent);
                    break;
            }
        }
        validateAndMerge(bestellkarteArchiv);
    }

}
