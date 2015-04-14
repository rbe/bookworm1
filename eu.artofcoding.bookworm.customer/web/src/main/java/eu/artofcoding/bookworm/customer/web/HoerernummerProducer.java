/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.web.jsf.FacesHelper;

import javax.enterprise.inject.Produces;

public class HoerernummerProducer {

    @Produces
    @Hoerernummer
    public String hoerernummer() {
        return FacesHelper.getRequestValue("hnr");
    }

}
