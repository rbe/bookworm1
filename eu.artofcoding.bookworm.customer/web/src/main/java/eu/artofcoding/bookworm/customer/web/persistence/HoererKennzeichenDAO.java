/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.hoerer.HoererKennzeichen;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class HoererKennzeichenDAO extends GenericDAO<HoererKennzeichen> implements Serializable {

    public HoererKennzeichenDAO() {
        super(HoererKennzeichen.class);
    }

    public HoererKennzeichen findByHoerernummer(final String hoerernummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("hoerernummer", hoerernummer);
        final List<HoererKennzeichen> hoererkennzeichen = dynamicFindWith(map, "");
        return hoererkennzeichen.get(0);
    }

}
