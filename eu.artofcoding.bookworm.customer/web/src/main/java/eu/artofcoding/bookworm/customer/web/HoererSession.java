/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.customer.web.jsf.FacesHelper;
import eu.artofcoding.bookworm.customer.web.persistence.MyData;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class HoererSession implements Serializable {

    @Inject
    private MyData myData;

    @PostConstruct
    private void postConstruct() {
        final String hoerernummer = FacesHelper.getRequestValue("hnr");
        final boolean hasHoerernummer = null != hoerernummer && !hoerernummer.isEmpty();
        if (hasHoerernummer) {
            myData.init(hoerernummer);
        }
    }

    public MyData getMyData() {
        return myData;
    }

}
