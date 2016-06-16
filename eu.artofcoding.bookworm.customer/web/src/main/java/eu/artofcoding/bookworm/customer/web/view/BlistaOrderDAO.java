/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.view;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateless
public class BlistaOrderDAO extends GenericDAO<BlistaOrder> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public BlistaOrderDAO() {
        super(BlistaOrder.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public List<BlistaOrder> findByTitleOrDatum(final String hoerernummer, final String titel, final Date datum) {
        TypedQuery<BlistaOrder> namedQuery;
        if (null == titel && null == datum) {
            namedQuery = entityManager.createNamedQuery("BlistaOrder.findByHoerernummerOrderByAusleihdatum", BlistaOrder.class);
        } else if (null != titel && datum == null) {
            namedQuery = entityManager.createNamedQuery("BlistaOrder.findByHoerernummerAndTitelOrderByAusleihdatum", BlistaOrder.class);
            namedQuery.setParameter("titel", titel);
        } else if (null != titel && null != datum) {
            namedQuery = entityManager.createNamedQuery("BlistaOrder.findByHoerernummerAndTitelAndAusleihdatumOrderByAusleihdatum", BlistaOrder.class);
            namedQuery.setParameter("titel", titel);
            namedQuery.setParameter("datum", datum, TemporalType.DATE);
        } else {
            throw new RuntimeException();
        }
        namedQuery.setParameter("hoerernummer", hoerernummer);
        return namedQuery.getResultList();
    }

}
