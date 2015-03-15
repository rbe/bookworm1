/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.datimport;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatimportMain {

    private static final AtomicBoolean completed = new AtomicBoolean(false);

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        // Spring Filesystem Application Context
        final String[] filesystemConfigLocation = new String[]{
                "conf/bookworm-datasource.xml",
                "conf/system/bookworm-spring-context.xml",
                "conf/bookworm-camel.xml"
        };
        final ApplicationContext applicationContext0 = new FileSystemXmlApplicationContext(filesystemConfigLocation);
        // Get Camel context
        final SpringCamelContext camel = (SpringCamelContext) applicationContext0.getBean("bookwormBooks");
        // Stop Camel when JVM shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    camel.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        // Await countdown latch... it's intended to wait forever
        while (!completed.get()) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
