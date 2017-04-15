/*
 * Bookworm
 *
 * Copyright (C) 2011-2016 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */
package eu.artofcoding.bookworm.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public final class SpringMain {

    private static final AtomicBoolean COMPLETED = new AtomicBoolean(false);

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void startSpringContext(final String[] filesystemContextLocations,
                                          final Function<ApplicationContext, Void> custom) throws Exception {
        final String bookwormHome = getAppHomeOrExit();
        final FileSystemXmlApplicationContext applicationContext =
                new FileSystemXmlApplicationContext(filesystemContextLocations);
        // Run custom code
        custom.apply(applicationContext);
        // Await countdown LATCH... it's intended to wait forever
        while (!COMPLETED.get()) {
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static void startSpringContext(final Class<?> appConfig,
                                          final Function<ApplicationContext, Void> custom) throws Exception {
        final AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.register(appConfig);
        applicationContext.refresh();
        // Run custom code
        custom.apply(applicationContext);
        // Await countdown LATCH... it's intended to wait forever
        while (!COMPLETED.get()) {
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static String getAppHomeOrExit() {
        final String bookwormHome = System.getenv("BOOKWORM_HOME");
        final boolean hasBookwormHome = null != bookwormHome && !bookwormHome.isEmpty();
        if (hasBookwormHome) {
            return bookwormHome;
        } else {
            System.out.println("Please set BOOKWORM_HOME");
            System.exit(1);
            return null;
        }
    }

}
