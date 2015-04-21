/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class BestellkarteDAO extends GenericDAO<Bestellkarte> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public BestellkarteDAO() {
        super(Bestellkarte.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public long countBooksByHoerernummer(final String hoerernummer) {
        final TypedQuery<Long> namedQuery = em.createNamedQuery("Bestellkarte.countBooksByHoerernummer", Long.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        return namedQuery.getSingleResult();
    }

    public Bestellkarte findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        Bestellkarte bestellkarte = null;
        try {
            bestellkarte = findOne("Bestellkarte.findByHoerernummer", map);
        } catch (NoResultException e) {
            // ignore
        }
        return bestellkarte;
    }

    public List<Book> findBooksByTitel(final String hoerernummer, final String titel) {
        final TypedQuery<Book> namedQuery = entityManager.createNamedQuery("Bestellkarte.findBooksByTitel", Book.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        namedQuery.setParameter("titel", titel);
        return namedQuery.getResultList();
    }

}
