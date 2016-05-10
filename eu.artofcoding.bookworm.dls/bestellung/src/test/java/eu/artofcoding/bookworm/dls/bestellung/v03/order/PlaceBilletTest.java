/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.v03.order;

import eu.artofcoding.bookworm.dls.bestellung.config.AppConfig;
import org.junit.Ignore;
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
@Ignore
public class PlaceBilletTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceBilletTest.class);

    @Autowired
    private BilletFactory billetFactory;

    @Autowired
    private BilletSender billetSender;

    @Test
    public void shouldCreateBillet() {
        final String userId = "titusTest";
        final String aghNummer = "1-0000122-3-9";
        final Billet billet = billetFactory.create(userId, aghNummer);
        System.out.println(billetSender.marshal(billet));
    }

    @Test
    @Ignore
    public void shouldPutFileIntoNew() throws InterruptedException {
        final String userId = "titusTest";
        final String aghNummer = "1-0000122-3-9";
        billetSender.sendToServer(userId, aghNummer);
        Thread.sleep(5 * 1000);
        assertEquals(BilletSender.ServerStatus.PROCESSED, billetSender.serverStatus(userId, aghNummer));
    }

    @Test
    public void shouldFindRejected() throws InterruptedException {
        final String userId = "titusTest";
        final String aghNummer = "1-0000122-3-9";
        final Billet billet = billetFactory.create(userId, aghNummer);
        billetSender.sendToServer(userId, aghNummer);
        Thread.sleep(5 * 1000);
        assertEquals(BilletSender.ServerStatus.REJECTED, billetSender.serverStatus(billet.getUserId(), billet.getAghNummer()));
    }

}
