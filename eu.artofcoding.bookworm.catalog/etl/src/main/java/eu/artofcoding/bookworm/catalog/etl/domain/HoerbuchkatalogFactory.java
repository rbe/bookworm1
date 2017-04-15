/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class HoerbuchkatalogFactory {

    private final Hoerbuchkatalog hoerbuchkatalog;

    @Autowired
    public HoerbuchkatalogFactory(final Hoerbuchkatalog hoerbuchkatalog) {
        this.hoerbuchkatalog = hoerbuchkatalog;
    }

    public Hoerbuchkatalog erzeugen(final Set<Hoerbuch> hoerbuecher) {
        hoerbuecher.forEach(hoerbuchkatalog::hinzufuegen);
        return hoerbuchkatalog;
    }

    public Hoerbuchkatalog erzeugen(final Set<Hoerbuch> hoerbuecher, final Set<AghNummer> aghNummern) {
        hoerbuecher.forEach(hoerbuch -> {
            final boolean blistaHatAghNummer = null != hoerbuch.getAghNummer() && aghNummern.contains(hoerbuch.getAghNummer());
            hoerbuch.setDownloadbar(blistaHatAghNummer);
            hoerbuchkatalog.hinzufuegen(hoerbuch);
        });
        return hoerbuchkatalog;
    }

}
