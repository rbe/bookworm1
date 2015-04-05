/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserHelper {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private static final SimpleDateFormat GERMAN_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static synchronized Date parseIsoDate(final String s) {
        try {
            final Date g = ISO_DATE_FORMAT.parse(s);
            return g;
        } catch (ParseException e) {
            // ignore
            return null;
        }
    }

    public static synchronized Date parseGermanDate(final String s) {
        try {
            final Date g = GERMAN_DATE_FORMAT.parse(s);
            return g;
        } catch (ParseException e) {
            // ignore
            return null;
        }
    }

    public static Integer parseInteger(final String s) {
        try {
            final Integer g = Integer.valueOf(s);
            return g;
        } catch (NumberFormatException e) {
            // ignore
            return null;
        }
    }

}
