/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class HoererstammDAO extends GenericDAO<Hoererstamm> implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public HoererstammDAO() {
        super(Hoererstamm.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

    public Hoererstamm findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        final List<Hoererstamm> hoererstamm = dynamicFindWith(map, "");
        return hoererstamm.size() > 0 ? hoererstamm.get(0) : null/*new Hoererstamm()*/;
    }

}
