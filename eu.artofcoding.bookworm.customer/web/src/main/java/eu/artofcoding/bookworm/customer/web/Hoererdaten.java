/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Date;

@Named
@RequestScoped
public class Hoererdaten extends AbstractHoererBean {

    public Hoererstamm getHoerer() {
        return hoererSession.getMyData().getHoererstamm();
    }

    public String getEmail() {
        return hoererSession.getMyData().getHoererKennzeichen().getEmail();
    }

    public Integer getMengenindex() {
        return hoererSession.getMyData().getMengenindex();
    }

    public boolean isSperrtermin() {
        final String sperrKz = hoererSession.getMyData().getSperrKz();
        return sperrKz != null && !sperrKz.isEmpty();
    }

    public Date getSperrTerminVon() {
        return hoererSession.getMyData().getSperrTerminVon();
    }

    public Date getSperrTerminBis() {
        return hoererSession.getMyData().getSperrTerminVon();
    }

    public boolean isUrlaub() {
        final String urlaubKennzeichen = getHoerer().getUrlaubKennzeichen();
        return urlaubKennzeichen != null && !urlaubKennzeichen.isEmpty();
    }

    public Date getRueckbuchungsdatum() {
        return hoererSession.getMyData().getRueckbuchungsdatum();
    }

}
