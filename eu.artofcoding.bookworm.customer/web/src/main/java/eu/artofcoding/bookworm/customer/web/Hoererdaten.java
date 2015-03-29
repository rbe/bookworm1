/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Date;

@Named
@RequestScoped
public class HoererDaten extends AbstractHoererBean {

    public Hoererstamm getHoerer() {
        return hoererSession.getMyData().getHoererstamm();
    }

    public String getEmail() {
        return hoererSession.getMyData().getHoererKennzeichen().getEmail();
    }

    public Integer getMengenindex() {
        return hoererSession.getMyData().getMengenindex();
    }

    public Date getSperrTerminVon() {
        return hoererSession.getMyData().getSperrTerminVon();
    }

    public Date getSperrTerminBis() {
        return hoererSession.getMyData().getSperrTerminVon();
    }

    public Date getRueckbuchungsdatum() {
        return hoererSession.getMyData().getRueckbuchungsdatum();
    }

}
