/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class AbstractEmbeddedJetty {

    protected final Class configurationClass;

    protected final String contextPath;

    protected final String MAPPING_URL = "/*";

    protected final String SPRING_DEFAULT_PROFILE = "dev";

    /**
     * @param springConfigurationClass See {@link AnnotationConfigWebApplicationContext#setConfigLocation(String)}.
     */
    protected AbstractEmbeddedJetty(final Class springConfigurationClass, final String contextPath) {
        this.configurationClass = springConfigurationClass;
        this.contextPath = contextPath;
    }

    protected ServletContextHandler getServletContextHandler(final WebApplicationContext context) throws IOException {
        final ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(contextPath);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        return contextHandler;
    }

    protected WebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(configurationClass.getName());
        context.getEnvironment().setDefaultProfiles(SPRING_DEFAULT_PROFILE);
        return context;
    }

    protected void start(int port) throws Exception {
        final Server server = new Server(new InetSocketAddress("127.0.0.1", port));
        server.setHandler(getServletContextHandler(getContext()));
        server.dumpStdErr();
        server.start();
        server.join();
    }

}
