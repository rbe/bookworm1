/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
public class HoererBuchstammDAO extends GenericDAO<HoererBuchstamm> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public HoererBuchstammDAO() {
        super(HoererBuchstamm.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public List<Belastung> findBelastungenBooksByTitleOrDatum(final String hoerernummer, final String titel, final Date datum) {
        TypedQuery<Belastung> namedQuery;
        if (null == datum) {
            namedQuery = entityManager.createNamedQuery("HoererBuchstamm.findBelastungenByHoerernummerAndBookTitel", Belastung.class);
        } else {
            namedQuery = entityManager.createNamedQuery("HoererBuchstamm.findBelastungenByHoerernummerAndBookTitelAndDatum", Belastung.class);
            namedQuery.setParameter("datum", datum, TemporalType.DATE);
        }
        namedQuery.setParameter("hoerernummer", hoerernummer);
        namedQuery.setParameter("titel", titel);
        return namedQuery.getResultList();
    }

}
