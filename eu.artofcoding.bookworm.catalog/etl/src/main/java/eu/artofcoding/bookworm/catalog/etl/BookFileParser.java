/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookFileParser {

    private static final SimpleDateFormat SDF_ISO = new SimpleDateFormat("yyyyMMdd");

    private static int[] pos = {
            0, 3,       // pos# 0, A
            4, 8,       // pos# 2, 12467
            9, 87,      // pos# 4, Abaelard, Petrus
            88, 207,    // pos# 6, Die Leidensgeschichte und der Briefwechsel mit Heloisa.
            208, 327,   // pos# 8, Erzählungen.
            328, 727,   // pos#10,
            728, 747,   // pos#12, Heidelberg
            748, 779,   // pos#14, Lambert Schneider
            780, 783,   // pos#16, 1979
            784, 833,   // pos#18, Lisa Bistrick
            834, 884,   // pos#20, Manfred Spitzer
            885, 890,   // pos#22, 434,00
            891, 912,   // pos#24, WBH Münster
            913, 916,   // pos#26, 1995
            917, 999,   // pos#28, Märchen
            1000, 1005, // pos#30, 1
            1006, 1008, // pos#32, 0
            1009, 1016,  // pos#34, 20001030
            1017, 0,  // pos#36, 01-0044470-1-9
    };

    private Book createBook(final String line) {
        Book book = new Book();
        // Parse line
        for (int i = 0; i < pos.length; i += 2) {
            int from = pos[i];
            int to = pos[i + 1];
            String substring = null;
            if (line.length() >= from) {
                if (to > 0) {
                    substring = line.substring(from, to + 1).trim();
                } else {
                    substring = line.substring(from).trim();
                }
                switch (i) {
                    case 0:
                        book.setSachgebiet(substring);
                        break;
                    case 2:
                        book.setTitelnummer(substring);
                        break;
                    case 4:
                        book.setAutor(substring);
                        break;
                    case 6:
                        book.setTitel(substring);
                        break;
                    case 8:
                        book.setUntertitel(substring);
                        break;
                    case 10:
                        book.setErlaeuterung(substring);
                        break;
                    case 12:
                        book.setVerlagsort(substring);
                        break;
                    case 14:
                        book.setVerlag(substring);
                        break;
                    case 16:
                        book.setDruckjahr(substring);
                        break;
                    case 18:
                        book.setSprecher1(substring);
                        break;
                    case 20:
                        book.setSprecher2(substring);
                        break;
                    case 22:
                        book.setSpieldauer(substring);
                        break;
                    case 24:
                        book.setProdOrt(substring);
                        break;
                    case 26:
                        book.setProdJahr(substring);
                        break;
                    case 28:
                        book.setSuchwoerter(substring);
                        break;
                    case 30:
                        book.setAnzahlCD(substring);
                        break;
                    case 32:
                        book.setTitelfamilie(substring);
                        break;
                    case 34:
                        try {
                            book.setEinstelldatum(new Date(SDF_ISO.parse(substring).getTime()));
                        } catch (ParseException e) {
                            // ignore
                        }
                        break;
                    case 36:
                        book.setAghNummer(substring);
                        break;
                }
            }
        }
        return book;
    }

    public List<GenericEntity> parse(final String body) throws IOException {
        // A line, terminated by CRLF
        String[] lines = body.split("\r\n");
        final List<GenericEntity> bookEntities = new ArrayList<>();
        for (String line : lines) {
            final boolean lineIsEmpty = line.trim().isEmpty();
            if (!lineIsEmpty) {
                final Book book = createBook(line);
                bookEntities.add(book);
            }
        }
        // Return all entities
        return bookEntities;
    }

    public List<GenericEntity> parse(final File file) throws IOException {
        // A line, terminated by CRLF
        final Path path = Paths.get(file.toURI());
        //ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 * 1024); // 4 MB buffer
        final BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-15"));
        final List<GenericEntity> bookEntities = new ArrayList<>();
        String line;
        while (null != (line = reader.readLine()) && line.length() > 1) {
            final Book book = createBook(line);
            bookEntities.add(book);
        }
        // Return all entities
        return bookEntities;
    }

}
