/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class HoerbuchFactory {

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
            1009, 1016, // pos#34, 20001030
            1017, 0,    // pos#36, 01-0044470-1-9
    };

    public Hoerbuch fromGesamtDat(final String line) {
        final SimpleDateFormat SDF_ISO = new SimpleDateFormat("yyyyMMdd");
        final Hoerbuch hoerbuch = new Hoerbuch();
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
                        hoerbuch.setSachgebiet(substring.trim().charAt(0));
                        break;
                    case 2:
                        hoerbuch.setTitelnummer(substring);
                        break;
                    case 4:
                        hoerbuch.setAutor(substring);
                        break;
                    case 6:
                        hoerbuch.setTitel(substring);
                        break;
                    case 8:
                        hoerbuch.setUntertitel(substring);
                        break;
                    case 10:
                        hoerbuch.setErlaeuterung(substring);
                        break;
                    case 12:
                        hoerbuch.setVerlagsort(substring);
                        break;
                    case 14:
                        hoerbuch.setVerlag(substring);
                        break;
                    case 16:
                        hoerbuch.setDruckjahr(substring);
                        break;
                    case 18:
                        hoerbuch.setSprecher1(substring);
                        break;
                    case 20:
                        hoerbuch.setSprecher2(substring);
                        break;
                    case 22:
                        hoerbuch.setSpieldauer(substring);
                        break;
                    case 24:
                        hoerbuch.setProdOrt(substring);
                        break;
                    case 26:
                        hoerbuch.setProdJahr(substring);
                        break;
                    case 28:
                        hoerbuch.setSuchwoerter(substring);
                        break;
                    case 30:
                        hoerbuch.setAnzahlCD(substring);
                        break;
                    case 32:
                        hoerbuch.setTitelfamilie(substring);
                        break;
                    case 34:
                        try {
                            final Date parse = SDF_ISO.parse(substring);
                            hoerbuch.setEinstelldatum(parse);
                        } catch (ParseException e) {
                            // ignore
                        }
                        break;
                    case 36:
                        hoerbuch.setAghNummer(substring);
                        break;
                }
            }
        }
        return hoerbuch;
    }

}
