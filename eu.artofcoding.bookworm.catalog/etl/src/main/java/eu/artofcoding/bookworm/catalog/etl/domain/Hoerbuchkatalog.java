/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * Aggregate
 */
@Component
@Scope(SCOPE_SINGLETON)
public class Hoerbuchkatalog {

    private final static long serialVersionUID = -1L;

    private final Map<Titelnummer, Hoerbuch> katalog;

    @Autowired
    public Hoerbuchkatalog(final Map<Titelnummer, Hoerbuch> katalog) {
        this.katalog = katalog;
    }

    void hinzufuegen(final Hoerbuch hoerbuch) {
        katalog.put(hoerbuch.getTitelnummer(), hoerbuch);
    }

    void herausnehmen(final Hoerbuch hoerbuch) {
        katalog.remove(hoerbuch.getTitelnummer());
    }

    public int anzahlHoerbuecher() {
        return katalog.size();
    }

    public void ausleihen(final Hoerbuch hoerbuch, final Hoerernummer hoerernummer) {
    }

    public void zurueckgeben(final Hoerbuch hoerbuch, final Hoerernummer hoerernummer) {
        throw new UnsupportedOperationException();
    }

}
