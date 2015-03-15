/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.datimport;

import eu.artofcoding.bookworm.api.book.Book;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookFileParser {

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
            1009, 1016  // pos#34, 20001030
    };

    public List<Book> parseFile(URI uri) throws IOException {
        // Setup date parser
        SimpleDateFormat sdfIso = new SimpleDateFormat("yyyyMMdd");
        // A line, terminated by CRLF
        File path = new File(uri);
        //ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 * 1024); // 4 MB buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("ISO-8859-15")));
        String line;
        List<Book> bookEntities = new ArrayList<>();
        Book book;
        while (null != (line = reader.readLine()) && line.length() > 1) {
            book = new Book();
            // Parse line
            for (int i = 0; i < pos.length; i += 2) {
                int from = pos[i];
                int to = pos[i + 1];
                final String substring = line.substring(from, to + 1).trim();
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
                            book.setEinstelldatum(new java.sql.Date(sdfIso.parse(substring).getTime()));
                        } catch (ParseException e) {
                            // ignore
                        }
                        break;
                }
            }
            bookEntities.add(book);
        }
        // Return all entities
        return bookEntities;
    }

}
