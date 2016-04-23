/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.v03.bestellung;

import eu.artofcoding.bookworm.common.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class BilletCreator {

    /*
<?xml version="1.0" encoding="utf-8"?>
<Billet>
<UserID>[Eindeutige ID des Benutzers]</UserID>
<BibliothekID>[Eindeutige Kennung der beauftragenden Bibliothek]</BibliothekID>
<Bestellnummer>[12 Stellige AGH-Nummer]</Bestellnummer>
<Abrufkennwort>[Beliebige Zeichenfolge zum Schutz des Downloads]</Abrufkennwort>
</Billet>
     */

    private final RandomStringGenerator randomStringGenerator;

    private final Template template;

    @Autowired
    public BilletCreator(final RandomStringGenerator randomStringGenerator, final Template template) {
        this.randomStringGenerator = randomStringGenerator;
        this.template = template;
    }

    /**
     * @param userId ID of user requesting a download.
     * @param bestellnummer Bestellnummer == AGH-Nummer.
     * @return Abrufkennwort.
     */
    public String createAsString(final String userId, final String bestellnummer) {
        final Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bestellnummer", bestellnummer);
        final String abrufkennwort = "abc123def";
        map.put("abrufkennwort", abrufkennwort);
        final URL url = BilletCreator.class.getResource("/dls/0.3/Billet.hbs");
        return template.render(url, map);
    }

    /**
     * @param userId ID of user requesting a download.
     * @param bestellnummer Bestellnummer == AGH-Nummer.
     * @return {@link Billet}.
     */
    public Billet create(final String userId, final String bestellnummer) {
        return new Billet(userId, bestellnummer, randomStringGenerator.next());
    }

}
