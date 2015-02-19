/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.bookimport;

import eu.artofcoding.bookworm.api.BookEntity;

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

    public List<BookEntity> parseFile(URI uri) throws IOException {
        // Setup date parser
        SimpleDateFormat sdfIso = new SimpleDateFormat("yyyyMMdd");
        // A line, terminated by CRLF
        File path = new File(uri);
        //ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 * 1024); // 4 MB buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("ISO-8859-15")));
        String line;
        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity bookEntity;
        while (null != (line = reader.readLine()) && line.length() > 1) {
            bookEntity = new BookEntity();
            // Parse line
            for (int i = 0; i < pos.length; i += 2) {
                int from = pos[i];
                int to = pos[i + 1];
                final String substring = line.substring(from, to + 1).trim();
                switch (i) {
                    case 0:
                        bookEntity.setSachgebiet(substring);
                        break;
                    case 2:
                        bookEntity.setTitelnummer(substring);
                        break;
                    case 4:
                        bookEntity.setAutor(substring);
                        break;
                    case 6:
                        bookEntity.setTitel(substring);
                        break;
                    case 8:
                        bookEntity.setUntertitel(substring);
                        break;
                    case 10:
                        bookEntity.setErlaeuterung(substring);
                        break;
                    case 12:
                        bookEntity.setVerlagsort(substring);
                        break;
                    case 14:
                        bookEntity.setVerlag(substring);
                        break;
                    case 16:
                        bookEntity.setDruckjahr(substring);
                        break;
                    case 18:
                        bookEntity.setSprecher1(substring);
                        break;
                    case 20:
                        bookEntity.setSprecher2(substring);
                        break;
                    case 22:
                        bookEntity.setSpieldauer(substring);
                        break;
                    case 24:
                        bookEntity.setProdOrt(substring);
                        break;
                    case 26:
                        bookEntity.setProdJahr(substring);
                        break;
                    case 28:
                        bookEntity.setSuchwoerter(substring);
                        break;
                    case 30:
                        bookEntity.setAnzahlCD(substring);
                        break;
                    case 32:
                        bookEntity.setTitelfamilie(substring);
                        break;
                    case 34:
                        try {
                            bookEntity.setEinstelldatum(new java.sql.Date(sdfIso.parse(substring).getTime()));
                        } catch (ParseException e) {
                            // ignore
                        }
                        break;
                }
            }
            bookEntities.add(bookEntity);
        }
        // Return all entities
        return bookEntities;
    }

}
