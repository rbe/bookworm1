/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public final class FacesHelper {

    public static String getRequestValue(final String requestParameter) {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return req.getParameter(requestParameter);
    }

}
