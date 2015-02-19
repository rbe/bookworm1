/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.hoerstp;

import java.text.SimpleDateFormat;
import java.util.Date;

class Hoererstamm {

    String hoerernummer;

    String anrede;

    String vorname;

    String nachname;

    String name2;

    String strasse;

    String plz;

    String ort;

    Date geburtsdatum;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String telefonnummer;

    String email;

    @Override
    public String toString() {
        String g = "null";
        if (null != geburtsdatum) {
            g = "'" + simpleDateFormat.format(geburtsdatum) + "'";
        }
        return String.format("INSERT INTO hoererstamm" +
                " (hoerernummer, anrede, vorname, nachname, name2, geburtsdatum, strasse, plz, ort, telefonnummer)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', %s, '%s', '%s', '%s', '%s');", hoerernummer, anrede, vorname, nachname, name2, g, strasse, plz, ort, telefonnummer);
    }

}
