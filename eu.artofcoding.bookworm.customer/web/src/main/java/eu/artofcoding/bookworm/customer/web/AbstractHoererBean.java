/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import javax.inject.Inject;

public class AbstractHoererBean {

    @Inject
    protected HoererSession hoererSession;

    protected String searchTitle = "";

    protected String searchDate = "";

    public String getHoerernummer() {
        return hoererSession.getMyData().getHoerernummer();
    }

    public String getVorname() {
        return hoererSession.getMyData().getVorname();
    }

    public String getNachname() {
        return hoererSession.getMyData().getNachname();
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

}
