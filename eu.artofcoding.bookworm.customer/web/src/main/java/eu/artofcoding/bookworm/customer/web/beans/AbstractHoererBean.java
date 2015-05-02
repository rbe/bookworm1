/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.beans;

import eu.artofcoding.bookworm.customer.web.HoererSession;

import javax.inject.Inject;

public class AbstractHoererBean {

    @Inject
    protected HoererSession hoererSession;

    protected String searchTitle = "";

    protected String searchDate = "";

    public String getHoerernummer() {
        return hoererSession.getHoerernummer();
    }

    public String getVorname() {
        return hoererSession.getVorname();
    }

    public String getNachname() {
        return hoererSession.getNachname();
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

    public boolean hasSearchTerms() {
        boolean hasSearchTitle = null != searchTitle && !searchTitle.trim().isEmpty();
        boolean hasSearchDate = null != searchDate && !searchDate.trim().isEmpty();
        return hasSearchTitle || hasSearchDate;
    }

}
