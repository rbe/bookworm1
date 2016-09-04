/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.hoerer;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public final class HoererDatenProducer {

    @PersistenceContext
    private EntityManager entityManager;

    private final String hoerernummer;

    @Inject
    private HoererDatenProducer(final @Hoerernummer String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    @Produces
    @HoererValue
    private Hoererstamm findHoererstammByHoerernummer() {
        final TypedQuery<Hoererstamm> namedQuery = entityManager.createNamedQuery("Hoererstamm.findByHoerernummer", Hoererstamm.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Produces
    @HoererValue
    private HoererKennzeichen findHoererKennzeichenByHoerernummer() {
        final TypedQuery<HoererKennzeichen> namedQuery = entityManager.createNamedQuery("HoererKennzeichen.findByHoerernummer", HoererKennzeichen.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Produces
    @HoererValue
    private HoererBuchstamm findHoererBuchstammByHoerernummer() {
        final TypedQuery<HoererBuchstamm> namedQuery = entityManager.createNamedQuery("HoererBuchstamm.findByHoerernummer", HoererBuchstamm.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Produces
    @HoererValue
    private Bestellkarte findByHoerernummer() {
        final TypedQuery<Bestellkarte> namedQuery = entityManager.createNamedQuery("Bestellkarte.findByHoerernummer", Bestellkarte.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Produces
    @HoererCount(query = "Bestellkarte.Books")
    private long countBooksByHoerernummer() {
        final TypedQuery<Long> namedQuery = entityManager.createNamedQuery("Bestellkarte.countBooksByHoerernummer", Long.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        try {
            return namedQuery.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Produces
    @HoererValue
    private ArrayList<BestellkarteArchiv> findBestellkarteArchivByHoerernummer() {
        final TypedQuery<BestellkarteArchiv> namedQuery = entityManager.createNamedQuery("BestellkarteArchiv.findByHoerernummerOrderByAusleihdatum", BestellkarteArchiv.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        final ArrayList<BestellkarteArchiv> arrayList = new ArrayList<>();
        arrayList.addAll(namedQuery.getResultList());
        return arrayList;
    }

}
