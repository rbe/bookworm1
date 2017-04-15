/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.domain;

import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BilletStatusService {

    private static final Logger LOGGER = Logger.getLogger(BilletStatusService.class);

    private final BilletSender billetSender;

    @Autowired
    public BilletStatusService(final BilletSender billetSender) {
        this.billetSender = billetSender;
    }

    public String invoke(final String userId, final String aghNummer) {
        MDC.put("userId", userId);
        MDC.put("aghNummer", aghNummer);
        try {
            final BilletSender.ServerStatus serverStatus = billetSender.serverStatus(userId, aghNummer);
            return serverStatus.name();
        } finally {
            MDC.clear();
        }
    }

}
