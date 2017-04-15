/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.katalog.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class BookAvailability {

    private String aghNummer;

    private boolean available;

    BookAvailability(final String aghNummer, final boolean available) {
        this.aghNummer = aghNummer;
        this.available = available;
    }

    BookAvailability() {
    }

    String getAghNummer() {
        return aghNummer;
    }

    void setAghNummer(final String aghNummer) {
        this.aghNummer = aghNummer;
    }

    boolean isAvailable() {
        return available;
    }

    void setAvailable(final boolean available) {
        this.available = available;
    }

}
