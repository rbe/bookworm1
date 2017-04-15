/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 */

package eu.artofcoding.bookworm.assembly;

import org.junit.Test;

public class WaitForContainerIT {

    private static final int WAIT = 20;

    @Test
    public void shouldStartContainer() {
        System.out.printf("Waiting %d seconds for container to startup%n", WAIT);
        try {
            Thread.sleep(WAIT * 1000);
        } catch (InterruptedException e) {
            System.out.printf("Interrupted waiting for container to startup: %s%n", e.getMessage());
        }
    }

}
