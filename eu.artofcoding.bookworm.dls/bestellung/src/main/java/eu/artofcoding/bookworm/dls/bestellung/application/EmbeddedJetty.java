/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.application;

import eu.artofcoding.bookworm.common.jetty.AbstractEmbeddedJetty;
import eu.artofcoding.bookworm.dls.bestellung.config.AppConfig;

public class EmbeddedJetty extends AbstractEmbeddedJetty {

    private EmbeddedJetty() {
        super(AppConfig.class, "/dls/bestellung");
    }

    public static void main(String[] args) throws Exception {
        final EmbeddedJetty embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start(9091);
    }

}
