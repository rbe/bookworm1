/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl;

import eu.artofcoding.bookworm.common.spring.SpringMain;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;

import java.util.function.Function;

public class DatimportMain {

    public static void main(String[] args) throws Exception {
        final String bookwormHome = SpringMain.getAppHomeOrExit();
        // Spring Filesystem Application Context
        final String[] filesystemContextLocations = new String[]{
                "file://" + bookwormHome + "/conf/system/bookworm-datasource.xml",
                "file://" + bookwormHome + "/conf/system/bookworm-spring-context.xml",
                "file://" + bookwormHome + "/conf/system/bookworm-camel.xml"
        };
        final Function<ApplicationContext, Void> custom = (applicationContext) -> {
            final SpringCamelContext camelContext = (SpringCamelContext) applicationContext.getBean("bookwormBooks");
            // Stop Camel when JVM shuts down
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    camelContext.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            return null;
        };
        SpringMain.startSpringContext(filesystemContextLocations, custom);
    }

}
