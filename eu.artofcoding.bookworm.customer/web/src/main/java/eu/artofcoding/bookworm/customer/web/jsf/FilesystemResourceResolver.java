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

public final class FilesystemResourceResolver extends ResourceResolver {

    private static final Logger LOGGER = Logger.getLogger(FilesystemResourceResolver.class.toString());

    private ResourceResolver parent;

    public FilesystemResourceResolver(final ResourceResolver parent) {
        this.parent = parent;
    }

    @Override
    public URL resolveUrl(final String path) {
        URL url = parent.resolveUrl(path);
        if (url == null && null != WebFilesystem.BASE_PATH) {
            try {
                url = new File(WebFilesystem.BASE_PATH, path).toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

}
