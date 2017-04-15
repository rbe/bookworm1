/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.katalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlistaConfiguration {

    @Value("${blista.dlscatalog.rest.scheme}")
    private String dlsCatalogRestScheme;

    @Value("${blista.dlscatalog.rest.host}")
    private String dlsCatalogRestHost;

    @Value("${blista.dlscatalog.rest.port}")
    private int dlsCatalogRestPort;

    @Value("${blista.dlscatalog.rest.uri}")
    private String dlsCatalogRestUri;

    public String getBlistaDlsCatalogUrl() {
        return String.format("%s://%s:%s%s", dlsCatalogRestScheme, dlsCatalogRestHost, dlsCatalogRestPort, dlsCatalogRestUri);
    }

}
