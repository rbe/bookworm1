/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.katalog.integration;

import eu.artofcoding.bookworm.dls.katalog.config.AppConfig;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AvailableBooksTest {

    @Autowired
    private AvailableBooks availableBooks;

    @Test @Ignore
    public void shouldFindBook() throws Exception {
        Assert.assertTrue(availableBooks.findBook("1-0079399-1-5"));
    }

}
