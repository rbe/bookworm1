/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.domain;

import eu.artofcoding.bookworm.common.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
class BilletFactory {

    private final Template template;

    @Autowired
    BilletFactory(final Template template) {
        this.template = template;
    }

    /**
     * @param userId ID of user requesting a download.
     * @param bestellnummer Bestellnummer == AGH-Nummer.
     * @return Abrufkennwort.
     */
    String createAsString(final String userId, final String bestellnummer) {
        final Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bestellnummer", bestellnummer);
        final String abrufkennwort = "abc123def";
        map.put("abrufkennwort", abrufkennwort);
        final URL url = BilletFactory.class.getResource("/dls/0.3/Billet.hbs");
        return template.render(url, map);
    }

    /**
     * @param userId ID of user requesting a download.
     * @param bestellnummer Bestellnummer == AGH-Nummer.
     * @return {@link Billet}.
     */
    Billet create(final String userId, final String bestellnummer) {
        return new Billet(userId, bestellnummer, RandomStringGenerator.next());
    }

}
