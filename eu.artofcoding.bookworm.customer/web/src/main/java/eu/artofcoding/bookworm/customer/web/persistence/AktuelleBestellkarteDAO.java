/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.hoerer.AktuelleBestellkarte;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class AktuelleBestellkarteDAO extends GenericDAO<AktuelleBestellkarte> implements Serializable {

    public AktuelleBestellkarteDAO() {
        super(AktuelleBestellkarte.class);
    }

    public AktuelleBestellkarte findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        final List<AktuelleBestellkarte> aktuelleBestellkarte = dynamicFindWith(map, "");
        return aktuelleBestellkarte.get(0);
    }

}
