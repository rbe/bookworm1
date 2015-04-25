/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatimportMain {

    private static final AtomicBoolean completed = new AtomicBoolean(false);

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        final String bookwormHome = System.getenv("BOOKWORM_HOME");
        final boolean hasBookwormHome = null == bookwormHome || bookwormHome.isEmpty();
        if (hasBookwormHome) {
            System.out.println("Please set BOOKWORM_HOME");
            System.exit(1);
        }
        System.out.println("BOOKWORM_HOME: " + bookwormHome);
        // Spring Filesystem Application Context
        final String[] filesystemConfigLocation = new String[]{
                bookwormHome + "/conf/system/bookworm-datasource.xml",
                bookwormHome + "/conf/system/bookworm-spring-context.xml",
                bookwormHome + "/conf/system/bookworm-camel.xml"
        };
        final ApplicationContext applicationContext0 = new FileSystemXmlApplicationContext(filesystemConfigLocation);
        final SpringCamelContext camelContext = (SpringCamelContext) applicationContext0.getBean("bookwormBooks");
        // Stop Camel when JVM shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    camelContext.stop();
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
