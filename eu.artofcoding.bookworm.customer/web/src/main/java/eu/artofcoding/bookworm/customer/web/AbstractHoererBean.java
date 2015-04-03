/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.customer.web.persistence.MyData;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class AbstractHoererBean {

    @Inject
    protected HoererSession hoererSession;

    protected String searchTitle = "";

    protected String searchDate = "tt.mm.jjjj";

    protected MyData myData;

    @PostConstruct
    private void postConstruct() {
        myData = hoererSession.getMyData();
    }

    public String getHoerernummer() {
        return null != myData ? myData.getHoerernummer() : "";
    }

    public String getVorname() {
        return null != myData ? myData.getVorname() : "";
    }

    public String getNachname() {
        return null != myData ? myData.getNachname() : "";
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
