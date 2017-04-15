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
public final class AghNummer implements Serializable {

    private final static long serialVersionUID = -1L;

    private final String aghNummer;

    public AghNummer(final String aghNummer) {
        this.aghNummer = aghNummer;
    }

    public String getAghNummer() {
        return aghNummer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AghNummer aghNummer1 = (AghNummer) o;
        return Objects.equals(aghNummer, aghNummer1.aghNummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aghNummer);
    }

    @Override
    public String toString() {
        return aghNummer;
    }

}
