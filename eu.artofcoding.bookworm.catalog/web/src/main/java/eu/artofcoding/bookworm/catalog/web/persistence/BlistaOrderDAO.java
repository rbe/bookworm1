/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

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
        //((EntityManagerImpl) em).getBroker().setAllowReferenceToSiblingContext(true);
    }

    /*
    @Override
    public BlistaOrder create(final BlistaOrder entity) {
        final Map<AghNummer, Abrufkennwort> aghAbrufkennwortMap = entity.getAghAbrufkennwortMap();
        for (AghNummer key : aghAbrufkennwortMap.keySet()) {
            em.persist(key);
            em.persist(aghAbrufkennwortMap.get(key));
        }
        return super.create(entity);
    }
    */

}
