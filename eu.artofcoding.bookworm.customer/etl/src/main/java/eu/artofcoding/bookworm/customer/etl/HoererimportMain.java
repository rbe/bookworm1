/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */
package eu.artofcoding.bookworm.customer.etl;

import eu.artofcoding.bookworm.common.spring.SpringMain;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.function.Function;

public class HoererimportMain {

    public static void main(String[] args) throws Exception {
        // Spring Filesystem Application Context
        final String bookwormHome = SpringMain.getAppHomeOrExit();
        final String[] filesystemContextLocations = new String[]{
                "file://" + bookwormHome + "/conf/system/bookworm-datasource.xml",
                "file://" + bookwormHome + "/conf/system/bookworm-spring-context.xml",
                "file://" + bookwormHome + "/conf/system/bookworm-camel-beans.xml",
                "file://" + bookwormHome + "/conf/system/bookworm-camel-routes.xml"
        };
        // Stop Camel when JVM shuts down
        final Function<ApplicationContext, Void> custom = (applicationContext) -> {
            FileSystemXmlApplicationContext c = (FileSystemXmlApplicationContext) applicationContext;
            c.registerShutdownHook();
            final SpringCamelContext camelContext = (SpringCamelContext) applicationContext.getBean("bookwormHoererImport");
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
