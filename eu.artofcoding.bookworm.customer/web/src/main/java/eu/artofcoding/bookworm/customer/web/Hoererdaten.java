/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Date;

@Named
@RequestScoped
public class Hoererdaten extends AbstractHoererBean {

    //<editor-fold desc="Hoererstamm">

    private Hoererstamm getHoererstamm() {
        return hoererSession.getMyData().getHoererstamm();
    }

    public Date getGeburtsdatum() {
        return getHoererstamm().getGeburtsdatum();
    }

    public String getStrasse() {
        return getHoererstamm().getStrasse();
    }

    public String getTelefonnummer() {
        return getHoererstamm().getTelefonnummer();
    }

    public String getUrlaubStrasse() {
        return getHoererstamm().getUrlaubStrasse();
    }

    public String getPlz() {
        return getHoererstamm().getPlz();
    }

    public String getAnrede() {
        return getHoererstamm().getAnrede();
    }

    public String getAdresszusatz() {
        return getHoererstamm().getAdresszusatz();
    }

    public String getOrt() {
        return getHoererstamm().getOrt();
    }

    public String getName2() {
        return getHoererstamm().getName2();
    }

    public String getUrlaubOrt() {
        return getHoererstamm().getUrlaubOrt();
    }

    public String getUrlaubName2() {
        return getHoererstamm().getUrlaubName2();
    }

    public String getUrlaubAdresszusatz() {
        return getHoererstamm().getUrlaubAdresszusatz();
    }

    public String getUrlaubPlz() {
        return getHoererstamm().getUrlaubPlz();
    }

    public boolean hasSperrtermin() {
        final Date sperrTerminVon = getHoererstamm().getSperrTerminVon();
        final Date sperrTerminBis = getHoererstamm().getSperrTerminBis();
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
        return getHoererstamm().getSperrTerminVon();
    }

    public Date getSperrTerminBis() {
        return getHoererstamm().getSperrTerminBis();
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

    public Date getUrlaubVon() {
        return getHoererstamm().getUrlaubVon();
    }

    public Date getUrlaubBis() {
        return getHoererstamm().getUrlaubBis();
    }

    //</editor-fold>

    private HoererKennzeichen getHoererKennzeichen() {
        return hoererSession.getMyData().getHoererKennzeichen();
    }

    public String getLand() {
        return getHoererKennzeichen().getLand();
    }

    public String getUrlaubLand() {
        return getHoererKennzeichen().getUrlaubLand();
    }

    public String getEmail() {
        return getHoererKennzeichen().getEmail();
    }

    private HoererBuchstamm getHoererBuchstamm() {
        return hoererSession.getMyData().getHoererBuchstamm();
    }

    public Integer getMengenindex() {
        return getHoererBuchstamm().getMengenindex();
    }

    public Date getRueckbuchungsdatum() {
        return getHoererBuchstamm().getRueckbuchungsdatum();
    }

}
