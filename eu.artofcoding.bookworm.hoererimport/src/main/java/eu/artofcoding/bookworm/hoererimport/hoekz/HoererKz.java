/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.hoererimport.hoekz;

class HoererKz {

    String hoerernummer;

    String email;

    @Override
    public String toString() {
        return String.format("UPDATE hoerer" +
                " SET email = '%s'" +
                " WHERE hoerernummer = '%s';", email, hoerernummer);
    }

}
