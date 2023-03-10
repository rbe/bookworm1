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
import eu.artofcoding.bookworm.common.helper.Strings;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.customer.etl.xml.AbstractXmlRowProcessor;

import javax.persistence.NoResultException;
import java.util.logging.Logger;

public class HoebstpXmlRowProcessor extends AbstractXmlRowProcessor<HoererBuchstamm> {

    private static final Logger LOGGER = Logger.getLogger(HoebstpXmlRowProcessor.class.getName());

    @Override
    public HoererBuchstamm xmlRowToEntity(final XmlRow xmlRow) {
        final HoererBuchstamm hoererBuchstamm = new HoererBuchstamm();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BUHNR":
                    hoererBuchstamm.setHoerernummer(tagContent);
                    break;
                case "BUANM":
                    hoererBuchstamm.setAnmeldedatum(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "BUMGI":
                    hoererBuchstamm.setMengenindex(ParserHelper.parseInteger(tagContent));
                    break;
                case "BURKZ":
                    hoererBuchstamm.setRueckbuchungskz(ParserHelper.parseInteger(tagContent));
                    break;
                case "BURDAT":
                    hoererBuchstamm.setRueckbuchungsdatum(ParserHelper.parseIsoDate(tagContent));
                    break;
                case "BULKZ":
                    hoererBuchstamm.setLoeschkennzeichen(tagContent);
                    break;
                default:
                    final boolean isBUBEL = xmlData.getTagName().startsWith("BUBEL") && Strings.notEmpty(tagContent) && !tagContent.equals("0");
                    final boolean isBUDAT = xmlData.getTagName().startsWith("BUDAT") && Strings.notEmpty(tagContent) && tagContent.length() == 8;
                    final boolean isBUKT = xmlData.getTagName().startsWith("BUKT");
                    // BUBEL1-27 == 24151410
                    //  Titelnummer ^^^^^
                    //                   ^^^ Boxnummer (immer die letzten drei)
                    if (isBUBEL) {
                        final String[] bubel = xmlData.getTagName().split("BUBEL");
                        final int index = Integer.parseInt(bubel[1]);
                        final int stellenBoxnummer = 3;
                        final String titelnummer = tagContent.substring(0, tagContent.length() - stellenBoxnummer);
                        final String boxnummer = tagContent.substring(tagContent.length() - stellenBoxnummer);
                        try {
                            final Book book = (Book) entityManager.createNamedQuery("Book.findByTitelnummer").setParameter("titelnummer", titelnummer).getSingleResult();
                            hoererBuchstamm.addBelastung(index, book, boxnummer);
                        } catch (NoResultException e) {
                            LOGGER.warning(String.format("No book with titelnummer %s found", titelnummer));
                        }
                    } else if (isBUDAT) { // BUDAT1-27 == 20150224
                        final String[] budat = xmlData.getTagName().split("BUDAT");
                        final int index = Integer.valueOf(budat[1]);
                        final Belastung belastung = hoererBuchstamm.getBelastung(index);
                        if (null != belastung) {
                            belastung.setDatum(ParserHelper.parseIsoDate(tagContent));
                        } else {
                            LOGGER.warning(String.format("[%s] Could not find Belastung for BUDAT%d", hoererBuchstamm.getHoerernummer(), index));
                        }
                    } else if (isBUKT) { // BUKT01-15
                    }
                    break;
            }
        }
        return hoererBuchstamm;
    }

}
