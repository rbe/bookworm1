/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class HoererKennzeichenDAO extends GenericDAO<HoererKennzeichen> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public HoererKennzeichenDAO() {
        super(HoererKennzeichen.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public HoererKennzeichen findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        return findOne("HoererKennzeichen.findByHoerernummer", map);
    }

}
