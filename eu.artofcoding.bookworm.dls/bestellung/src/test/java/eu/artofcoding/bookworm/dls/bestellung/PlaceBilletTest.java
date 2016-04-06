/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung;

import eu.artofcoding.bookworm.dls.v03.bestellung.Billet;
import eu.artofcoding.bookworm.dls.v03.bestellung.BilletCreator;
import eu.artofcoding.bookworm.dls.v03.bestellung.BilletSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PlaceBilletTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceBilletTest.class);

    @Autowired
    private BilletCreator billetCreator;

    @Autowired
    private BilletSender billetSender;

    @Test
    public void shouldCreateBillet() {
        final Billet billet = billetCreator.create("titusTest", "1-0000122-3-9");
        System.out.println(billetSender.marshal(billet));
    }

    @Test
    public void shouldPutFileIntoNew() throws InterruptedException {
        final Billet billet = billetCreator.create("titusTest", "1-0000122-3-9");
        billetSender.sendToServer(billet);
        LOGGER.info("Sent billet: {}", billet);
        Thread.sleep(5 * 1000);
        assertEquals(BilletSender.ServerStatus.PROCESSED, billetSender.serverStatus(billet));
    }

    @Test
    public void shouldFindRejected() throws InterruptedException {
        final Billet billet = billetCreator.create("titusTest", "1234");
        billetSender.sendToServer(billet);
        Thread.sleep(5 * 1000);
        assertEquals(BilletSender.ServerStatus.REJECTED, billetSender.serverStatus(billet));
    }

}
