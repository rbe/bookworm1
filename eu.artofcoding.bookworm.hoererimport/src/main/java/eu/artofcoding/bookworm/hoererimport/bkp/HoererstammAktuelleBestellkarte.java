/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.bkp;

import java.text.SimpleDateFormat;
import java.util.Date;

class HoererstammAktuelleBestellkarte {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String hoerernummer;

    String[] titelnummern = new String[400];

    Date datumStand;

    @Override
    public String toString() {
        String s = "null";
        if (null != datumStand) {
            s = "'" + simpleDateFormat.format(datumStand) + "'";
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final int titelnummernMaxIndex = titelnummern.length - 1;
        for (int i = 0; i < titelnummern.length; i++) {
            final String t = titelnummern[i];
            final boolean hasContent = null != t && !t.isEmpty();
            if (hasContent) {
                if (i > 0 && i < titelnummernMaxIndex) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(t);
            } else {
                break;
            }
        }
        return String.format("INSERT INTO hoererstamm_aktuelle_bestellkarte" +
                " (hoerernummer, titelnummern, datumStand)" +
                " VALUES ('%s', '%s', %s);", hoerernummer, stringBuilder.toString(), s);
    }

}
