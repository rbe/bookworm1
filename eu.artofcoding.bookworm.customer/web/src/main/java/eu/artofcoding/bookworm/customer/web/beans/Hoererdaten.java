/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.beans;

import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Named
@RequestScoped
public class Hoererdaten extends AbstractHoererBean {

    private boolean isDateWithinRange(final Date dateFrom, final Date dateTo) {
        final Date now = getTodayWithTime0();
        final boolean nowAfterOrEqualToDateFrom = now.after(dateFrom) || now.equals(dateFrom);
        final boolean nowBeforeOrEqualToDateTo = now.before(dateTo) || now.equals(dateTo);
        return nowAfterOrEqualToDateFrom && nowBeforeOrEqualToDateTo;
    }

    private Date getTodayWithTime0() {
        final Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    //<editor-fold desc="Hoererstamm">

    private Hoererstamm getHoererstamm() {
        return hoererSession.getHoererstamm();
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
        return haveSperrtermin && isDateWithinRange(sperrTerminVon, sperrTerminBis);
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
        return haveUrlaub && isDateWithinRange(urlaubVon, urlaubBis);
    }

    public Date getUrlaubVon() {
        return getHoererstamm().getUrlaubVon();
    }

    public Date getUrlaubBis() {
        return getHoererstamm().getUrlaubBis();
    }

    //</editor-fold>

    private HoererKennzeichen getHoererKennzeichen() {
        return hoererSession.getHoererKennzeichen();
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
        return hoererSession.getHoererBuchstamm();
    }

    public Integer getMengenindex() {
        final HoererBuchstamm hoererBuchstamm = getHoererBuchstamm();
        return null != hoererBuchstamm ? hoererBuchstamm.getMengenindex() : 0;
    }

    public Date getRueckbuchungsdatum() {
        return getHoererBuchstamm().getRueckbuchungsdatum();
    }

}
