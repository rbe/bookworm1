/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlistaConfiguration {

    @Value("${blista.dls.rest.scheme}")
    private String dlsRestScheme;

    @Value("${blista.dls.rest.host}")
    private String dlsRestHost;

    @Value("${blista.dls.rest.port}")
    private int dlsRestPort;

    @Value("${blista.dls.rest.uri}")
    private String dlsRestUri;

    @Value("${blista.dls.rest.bibliothek}")
    private String bibliothek;

    @Value("${blista.dls.rest.bibkennwort}")
    private String bibkennwort;

    public String getBlistaDlsUrl() {
        return String.format("%s://%s:%s%s", dlsRestScheme, dlsRestHost, dlsRestPort, dlsRestUri);
    }

    public String getBibliothek() {
        return bibliothek;
    }

    public String getBibkennwort() {
        return bibkennwort;
    }

}
