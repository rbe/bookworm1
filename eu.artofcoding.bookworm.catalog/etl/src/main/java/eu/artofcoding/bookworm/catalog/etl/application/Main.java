/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.application;

import eu.artofcoding.bookworm.common.spring.SpringMain;

public class Main {

    public static void main(String[] args) throws Exception {
        SpringMain.startSpringContext(AppConfig.class, applicationContext -> null);
    }

}
