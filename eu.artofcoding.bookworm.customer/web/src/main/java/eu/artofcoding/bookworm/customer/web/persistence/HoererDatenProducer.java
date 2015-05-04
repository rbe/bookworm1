/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.qualifier.HoererCount;
import eu.artofcoding.bookworm.customer.web.qualifier.HoererValue;
import eu.artofcoding.bookworm.customer.web.qualifier.Hoerernummer;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public final class HoererDatenProducer {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    @Hoerernummer
    private String hoerernummer;

    private HoererDatenProducer() {
    }

    @Produces @HoererValue
    private Hoererstamm findHoererstammByHoerernummer() {
        final TypedQuery<Hoererstamm> namedQuery = entityManager.createNamedQuery("Hoererstamm.findByHoerernummer", Hoererstamm.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final Hoererstamm singleResult = namedQuery.getSingleResult();
        return singleResult;
    }

    @Produces @HoererValue
    private HoererKennzeichen findHoererKennzeichenByHoerernummer() {
        final TypedQuery<HoererKennzeichen> namedQuery = entityManager.createNamedQuery("HoererKennzeichen.findByHoerernummer", HoererKennzeichen.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final HoererKennzeichen singleResult = namedQuery.getSingleResult();
        return singleResult;
    }

    @Produces @HoererValue
    private HoererBuchstamm findHoererBuchstammByHoerernummer() {
        final TypedQuery<HoererBuchstamm> namedQuery = entityManager.createNamedQuery("HoererBuchstamm.findByHoerernummer", HoererBuchstamm.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final HoererBuchstamm singleResult = namedQuery.getSingleResult();
        return singleResult;
    }

    @Produces @HoererValue
    private Bestellkarte findByHoerernummer() {
        final TypedQuery<Bestellkarte> namedQuery = entityManager.createNamedQuery("Bestellkarte.findByHoerernummer", Bestellkarte.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final Bestellkarte singleResult = namedQuery.getSingleResult();
        return singleResult;
    }

    @Produces @HoererCount(query = "Bestellkarte.Books")
    private long countBooksByHoerernummer() {
        final TypedQuery<Long> namedQuery = entityManager.createNamedQuery("Bestellkarte.countBooksByHoerernummer", Long.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        return namedQuery.getSingleResult();
    }

    @Produces @HoererValue
    private ArrayList<BestellkarteArchiv> findBestellkarteArchivByHoerernummer() {
        final TypedQuery<BestellkarteArchiv> namedQuery = entityManager.createNamedQuery("BestellkarteArchiv.findByHoerernummerOrderByAusleihdatum", BestellkarteArchiv.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final ArrayList<BestellkarteArchiv> arrayList = new ArrayList<>();
        arrayList.addAll(namedQuery.getResultList());
        return arrayList;
    }

}
