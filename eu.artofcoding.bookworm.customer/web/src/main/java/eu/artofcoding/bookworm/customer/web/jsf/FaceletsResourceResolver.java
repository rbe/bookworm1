/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.jsf;

import javax.faces.view.facelets.ResourceResolver;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class FaceletsResourceResolver extends ResourceResolver {

    private static final Logger LOGGER = Logger.getLogger(FaceletsResourceResolver.class.toString());

    private static final String TEMPLATE_SYSTEM_VARIABLE = "WBH_CUSTOMER_TEMPLATE";

    private static final String BASE_PATH;

    private ResourceResolver parent;

    static {
        BASE_PATH = System.getenv(TEMPLATE_SYSTEM_VARIABLE);
        if (null == BASE_PATH) {
            LOGGER.warning(String.format("Variable %s not set!", TEMPLATE_SYSTEM_VARIABLE));
        }
    }

    public FaceletsResourceResolver(final ResourceResolver parent) {
        this.parent = parent;
    }

    @Override
    public URL resolveUrl(final String path) {
        URL url = parent.resolveUrl(path);
        if (url == null && null != BASE_PATH) {
            try {
                url = new File(BASE_PATH, path).toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

}
