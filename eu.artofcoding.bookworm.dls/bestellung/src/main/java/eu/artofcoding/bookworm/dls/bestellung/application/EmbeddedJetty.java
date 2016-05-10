package eu.artofcoding.bookworm.dls.bestellung.application;

import eu.artofcoding.bookworm.dls.bestellung.config.AppConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class EmbeddedJetty {

    private static final int DEFAULT_PORT = 8095;

    private static final String CONTEXT_PATH = "/";

    private static final String CONFIG_LOCATION = AppConfig.class.getPackage().getName();

    private static final String MAPPING_URL = "/*";

    private static final String DEFAULT_PROFILE = "dev";

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(DEFAULT_PORT);
    }

    private static ServletContextHandler getServletContextHandler(final WebApplicationContext context) throws IOException {
        final ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        //contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }

    private void startJetty(int port) throws Exception {
        final Server server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));
        server.dumpStdErr();
        server.start();
        server.join();
    }

}
