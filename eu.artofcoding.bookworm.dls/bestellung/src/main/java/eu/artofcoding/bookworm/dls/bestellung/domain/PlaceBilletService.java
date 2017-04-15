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
public class PlaceBilletService {

    private static final Logger LOGGER = Logger.getLogger(PlaceBilletService.class);

    private final BilletSender billetSender;

    @Autowired
    public PlaceBilletService(BilletSender billetSender) {
        this.billetSender = billetSender;
    }

    public BookOrder invoke(final String userId, final String aghNummer) {
        MDC.put("userId", userId);
        MDC.put("aghNummer", aghNummer);
        BookOrder bookOrder;
        try {
            final String abrufkennwort = billetSender.sendToServer(userId, aghNummer);
            final BilletSender.ServerStatus serverStatus = billetSender.serverStatus(userId, aghNummer);
            bookOrder = new BookOrder(abrufkennwort,
                    null != serverStatus ? serverStatus.name() : BilletSender.ServerStatus.NEW.name());
        } catch (Exception e) {
            LOGGER.error("Cannot submit order for userId=" + userId + " aghNummer=" + aghNummer, e);
            bookOrder = new BookOrder("", BilletSender.ServerStatus.NEW.name()); // TODO
        } finally {
            MDC.clear();
        }
        return bookOrder;
    }

}
