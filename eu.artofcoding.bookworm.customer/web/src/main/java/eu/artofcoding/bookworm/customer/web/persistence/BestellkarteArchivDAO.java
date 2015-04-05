/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class BestellkarteArchivDAO extends GenericDAO<BestellkarteArchiv> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public BestellkarteArchivDAO() {
        super(BestellkarteArchiv.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public long countByHoerernummer(final String hoerernummer) {
        final TypedQuery<Long> namedQuery = entityManager.createNamedQuery("BestellkarteArchiv.countByHoerernummer", Long.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        return namedQuery.getSingleResult();
    }

    public List<BestellkarteArchiv> findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        return findAll("BestellkarteArchiv.findByHoerernummerOrderByAusleihdatum", map);
    }

    public List<BestellkarteArchiv> findByTitleOrDatum(final String hoerernummer, final String titel, final Date datum) {
        TypedQuery<BestellkarteArchiv> namedQuery;
        if (null == datum) {
            namedQuery = entityManager.createNamedQuery("BestellkarteArchiv.findByHoerernummerAndTitelOrderByAusleihdatum", BestellkarteArchiv.class);
        } else {
            namedQuery = entityManager.createNamedQuery("BestellkarteArchiv.findByHoerernummerAndTitelAndAusleihdatumOrderByAusleihdatum", BestellkarteArchiv.class);
            namedQuery.setParameter("datum", datum, TemporalType.DATE);
        }
        namedQuery.setParameter("hoerernummer", hoerernummer);
        namedQuery.setParameter("titel", titel);
        return namedQuery.getResultList();
    }

}
