/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */
package eu.artofcoding.bookworm.dls.etl.application;

import eu.artofcoding.bookworm.common.spring.SpringMain;
import org.springframework.context.ApplicationContext;

import java.util.function.Function;

public class BlistaEtlMain {

    public static void main(String[] args) throws Exception {
        final Function<ApplicationContext, Void> custom = (applicationContext) -> null;
        SpringMain.startSpringContext(custom);
    }

}
