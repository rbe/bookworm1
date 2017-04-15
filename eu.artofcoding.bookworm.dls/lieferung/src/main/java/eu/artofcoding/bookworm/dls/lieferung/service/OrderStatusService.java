/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
class OrderStatusService {

    private final BlockingQueue<String> blockingQueue;

    private int counter;

    @Autowired
    OrderStatusService(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Async
    void startConsuming() {
        try {
            String s;
            while (true) {
                s = blockingQueue.take();
                counter++;
                System.out.println(counter + " -> " + s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
