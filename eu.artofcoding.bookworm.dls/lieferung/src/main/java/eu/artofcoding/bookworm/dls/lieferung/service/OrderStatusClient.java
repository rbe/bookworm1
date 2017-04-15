/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
class OrderStatusClient {

    private final BlockingQueue<String> blockingQueue;

    @Autowired
    OrderStatusClient(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    void sendOrder(final String userId, final String aghNummer) throws InterruptedException {
        blockingQueue.offer(String.format("%s/%s", userId, aghNummer));
    }

}
