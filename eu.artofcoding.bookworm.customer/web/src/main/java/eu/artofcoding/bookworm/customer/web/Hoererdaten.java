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

    public Hoererstamm getHoererstamm() {
        return hoererSession.getMyData().getHoererstamm();
    }

    public String getEmail() {
        return hoererSession.getMyData().getHoererKennzeichen().getEmail();
    }

    public Integer getMengenindex() {
        return hoererSession.getMyData().getMengenindex();
    }

    public boolean hasSperrtermin() {
        final Date sperrTerminVon = hoererSession.getMyData().getSperrTerminVon();
        final Date sperrTerminBis = hoererSession.getMyData().getSperrTerminBis();
        final boolean haveSperrtermin = null != sperrTerminVon && null != sperrTerminBis;
        if (haveSperrtermin) {
            final Date now = new Date();
            final boolean nowBeforeOrEqualToSperrTerminVon = now.equals(sperrTerminVon) || now.after(sperrTerminVon);
            final boolean nowBeforeOrEqualToSperrTerminBis = now.before(sperrTerminBis) || now.equals(sperrTerminBis);
            final boolean nowInRangeOfSperrtermin = nowBeforeOrEqualToSperrTerminVon && nowBeforeOrEqualToSperrTerminBis;
            return nowInRangeOfSperrtermin;
        } else {
            return false;
        }
    }

    public Date getSperrTerminVon() {
        return hoererSession.getMyData().getSperrTerminVon();
    }

    public Date getSperrTerminBis() {
        return hoererSession.getMyData().getSperrTerminBis();
    }

    public boolean hasUrlaub() {
        final Date urlaubVon = getHoererstamm().getUrlaubVon();
        final Date urlaubBis = getHoererstamm().getUrlaubBis();
        final boolean haveUrlaub = null != urlaubVon && null != urlaubBis;
        if (haveUrlaub) {
            final Date now = new Date();
            final boolean nowBeforeOrEqualToUrlaubVon = now.equals(urlaubVon) || now.after(urlaubVon);
            final boolean nowBeforeOrEqualToUrlaubBis = now.before(urlaubBis) || now.equals(urlaubBis);
            final boolean nowInRangeOfUrlaub = nowBeforeOrEqualToUrlaubVon && nowBeforeOrEqualToUrlaubBis;
            return nowInRangeOfUrlaub;
        } else {
            return false;
        }
    }

    public Date getRueckbuchungsdatum() {
        return hoererSession.getMyData().getRueckbuchungsdatum();
    }

}
