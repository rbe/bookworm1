/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.domain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RandomStringGeneratorTest {

    @Test
    public void next() throws Exception {
        for (int i = 0; i < 1_000; i++) {
            final String next = RandomStringGenerator.next();
            assertNotNull(next);
        }
    }

}
