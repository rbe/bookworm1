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
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Named
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

    public List<Book> findBooksByTitel(final String hoerernummer, final String titel) {
        final TypedQuery<Book> namedQuery = entityManager.createNamedQuery("Bestellkarte.findBooksByTitel", Book.class);
        namedQuery.setParameter("hoerernummer", hoerernummer);
        namedQuery.setParameter("titel", titel);
        return namedQuery.getResultList();
    }

}
