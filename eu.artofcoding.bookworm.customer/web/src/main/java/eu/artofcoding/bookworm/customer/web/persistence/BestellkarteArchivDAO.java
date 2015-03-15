/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.hoerer.BestellkarteArchiv;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class BestellkarteArchivDAO extends GenericDAO<BestellkarteArchiv> implements Serializable {

    public BestellkarteArchivDAO() {
        super(BestellkarteArchiv.class);
    }

    public BestellkarteArchiv findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        final List<BestellkarteArchiv> bestellkarteArchiv = dynamicFindWith(map, "");
        return bestellkarteArchiv.get(0);
    }

}
