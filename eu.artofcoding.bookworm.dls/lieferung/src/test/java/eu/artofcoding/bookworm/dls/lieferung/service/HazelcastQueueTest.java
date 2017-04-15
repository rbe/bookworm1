/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderTestAppConfig.class)
@Ignore
public class HazelcastQueueTest {

    @Autowired
    private OrderStatusClient orderStatusClient;

    @Autowired
    private OrderStatusService orderStatusService;

    @Test
    public void should() throws InterruptedException {
        orderStatusService.startConsuming();
        orderStatusClient.sendOrder("80170", "1-0000122-3-9");
    }

}
