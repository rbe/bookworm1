/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Value Object
 */
public class Hoerernummer implements Serializable {

    private final static long serialVersionUID = -1L;

    private final String hoerernummer;

    public Hoerernummer(final String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Hoerernummer that = (Hoerernummer) o;
        return Objects.equals(hoerernummer, that.hoerernummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoerernummer);
    }

    @Override
    public String toString() {
        return hoerernummer;
    }

}
