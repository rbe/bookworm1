/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.hoererimport.as400xml;

import eu.artofcoding.bookworm.api.book.Book;
import eu.artofcoding.bookworm.api.helper.SqlStatement;
import eu.artofcoding.bookworm.api.hoerer.Belastung;
import eu.artofcoding.bookworm.api.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.api.xml.XmlData;
import eu.artofcoding.bookworm.api.xml.XmlRow;
import eu.artofcoding.bookworm.customer.hoererimport.Strings;

import javax.persistence.NoResultException;

public class HoebstpXmlRowProcessor extends AbstractXmlRowProcessor {

    @Override
    public void xmlRowToEntity(final XmlRow xmlRow) {
        final HoererBuchstamm hoererBuchstamm = new HoererBuchstamm();
        for (final XmlData xmlData : xmlRow.getXmlDatas()) {
            final String tagContent = xmlData.getTagContent();
            switch (xmlData.getTagName()) {
                case "BUHNR":
                    hoererBuchstamm.setHoerernummer(tagContent);
                    break;
                case "BUANM":
                    hoererBuchstamm.setAnmeldedatum(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "BUMGI":
                    hoererBuchstamm.setMengenindex(SqlStatement.parseInteger(tagContent));
                    break;
                case "BUVON":
                    hoererBuchstamm.setSperrTerminVon(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "BUBIS":
                    hoererBuchstamm.setSperrTerminBis(SqlStatement.parseIsoDate(tagContent));
                    break;
                case "BUSPR":
                    hoererBuchstamm.setSperrKz(tagContent);
                    break;
                case "BUBKKZ":
                    hoererBuchstamm.setBestellkkz(tagContent);
                    break;
                case "BUSGB":
                    /*
                    final boolean hasValue = null != tagContent && !tagContent.isEmpty();
                    if (hasValue) {
                        final Set<Sachgebiet> sachgebiete = new TreeSet<>();
                        for (char c : tagContent.toCharArray()) {
                            sachgebiete.add(null);
                        }
                        hoererBuchstamm.setSachgebiet(sachgebiete);
                    }
                    */
                    break;
                case "BURKZ":
                    hoererBuchstamm.setRueckbuchungskz(SqlStatement.parseInteger(tagContent));
                    break;
                case "BURDAT":
                    hoererBuchstamm.setRueckbuchungsdatum(SqlStatement.parseIsoDate(tagContent));
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
                        final int index = Integer.valueOf(bubel[1]);
                        final int stellenBoxnummer = 3;
                        final String titelnummer = tagContent.substring(0, tagContent.length() - stellenBoxnummer);
                        final String boxnummer = tagContent.substring(tagContent.length() - stellenBoxnummer);
                        final Belastung belastung = hoererBuchstamm.addBelastung(index, titelnummer, boxnummer);
                        try {
                            final Book book = (Book) entityManager.createNamedQuery("Book.findByTitelnummer").setParameter("titelnummer", titelnummer).getSingleResult();
                            belastung.setBook(book);
                        } catch (NoResultException e) {
                            LOGGER.warning(String.format("No book with titelnummer %s found", titelnummer));
                        }
                    } else if (isBUDAT) { // BUDAT1-27 == 20150224
                        final String[] budat = xmlData.getTagName().split("BUDAT");
                        final int index = Integer.valueOf(budat[1]);
                        hoererBuchstamm.getBelastung(index).setDatum(SqlStatement.parseIsoDate(tagContent));
                    } else if (isBUKT) { // BUKT01-15
                    }
                    break;
            }
        }
        validateAndMerge(hoererBuchstamm);
    }

}
