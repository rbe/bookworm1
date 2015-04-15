/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.web.jsf;

import java.util.logging.Logger;

public final class WebFilesystem {

    private static final Logger LOGGER = Logger.getLogger(WebFilesystem.class.toString());

    static final String TEMPLATE_SYSTEM_VARIABLE = "WBH_TEMPLATE";

    static final String BASE_PATH;

    static {
        BASE_PATH = System.getenv(TEMPLATE_SYSTEM_VARIABLE);
        if (null == WebFilesystem.BASE_PATH) {
            LOGGER.warning(String.format("Variable %s not set!", TEMPLATE_SYSTEM_VARIABLE));
        }
    }

}
