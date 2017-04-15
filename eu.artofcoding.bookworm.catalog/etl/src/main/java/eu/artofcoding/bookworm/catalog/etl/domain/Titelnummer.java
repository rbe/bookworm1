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
public final class Titelnummer implements Serializable {

    private final static long serialVersionUID = -1L;

    private final String titelnummer;

    Titelnummer(final String titelnummer) {
        this.titelnummer = titelnummer;
    }

    public String getTitelnummer() {
        return titelnummer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Titelnummer that = (Titelnummer) o;
        return Objects.equals(titelnummer, that.titelnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titelnummer);
    }

    @Override
    public String toString() {
        return titelnummer;
    }

}
