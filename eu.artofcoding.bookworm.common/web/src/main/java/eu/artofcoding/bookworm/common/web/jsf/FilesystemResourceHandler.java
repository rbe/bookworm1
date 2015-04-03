/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.web.jsf;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import java.io.IOException;

public final class FilesystemResourceHandler extends ResourceHandler {

    @Override
    public Resource createResource(String s) {
        return null;
    }

    @Override
    public Resource createResource(String s, String s1) {
        return null;
    }

    @Override
    public Resource createResource(String s, String s1, String s2) {
        return null;
    }

    @Override
    public boolean libraryExists(String s) {
        return false;
    }

    @Override
    public void handleResourceRequest(FacesContext facesContext) throws IOException {
    }

    @Override
    public boolean isResourceRequest(FacesContext facesContext) {
        return false;
    }

    @Override
    public String getRendererTypeForResourceName(String s) {
        return null;
    }

}
