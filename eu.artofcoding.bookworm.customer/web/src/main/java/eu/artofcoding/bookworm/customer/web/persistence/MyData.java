/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.Hoerernummer;
import eu.artofcoding.bookworm.customer.web.NoHoerernummerException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateful
public class MyData implements Serializable {

    //<editor-fold desc="Member">

    @EJB
    private transient HoererstammDAO hoererstammDAO;

    @EJB
    private transient HoererBuchstammDAO hoererBuchstammDAO;

    @EJB
    private transient HoererKennzeichenDAO hoererKennzeichenDAO;

    @EJB
    private transient BestellkarteDAO bestellkarteDAO;

    @EJB
    private transient BestellkarteArchivDAO bestellkarteArchivDAO;

    @Inject
    @Hoerernummer
    private String hoerernummer;

    private Hoererstamm hoererstamm;

    private HoererKennzeichen hoererKennzeichen;

    private HoererBuchstamm hoererBuchstamm;

    private Bestellkarte bestellkarte;

    private List<BestellkarteArchiv> bestellkarteArchiv;

    //</editor-fold>

    //<editor-fold desc="Lifecycle">

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        if (null == hoerernummer) {
            throw new NoHoerernummerException();
        }
        return ctx.proceed();
    }

    //</editor-fold>

    //<editor-fold desc="Hoererstamm">

    public String getHoerernummer() {
        return getHoererstamm().getHoerernummer();
    }

    public String getNachname() {
        return getHoererstamm().getNachname();
    }

    public String getVorname() {
        return getHoererstamm().getVorname();
    }

    public Hoererstamm getHoererstamm() {
        if (null == hoererstamm) {
            hoererstamm = hoererstammDAO.findByHoerernummer(hoerernummer);
        }
        return hoererstamm;
    }

    //</editor-fold>

    //<editor-fold desc="HoererBuchstamm">

    public Date getSperrTerminVon() {
        return getHoererstamm().getSperrTerminVon();
    }

    public Date getSperrTerminBis() {
        return getHoererstamm().getSperrTerminBis();
    }

    public List<Belastung> getBelastungen() {
        return getHoererBuchstamm().getBelastungen();
    }

    public Integer getMengenindex() {
        return getHoererBuchstamm().getMengenindex();
    }

    public Date getRueckbuchungsdatum() {
        return getHoererBuchstamm().getRueckbuchungsdatum();
    }

    //</editor-fold>

    public HoererBuchstamm getHoererBuchstamm() {
        if (null == hoererBuchstamm) {
            hoererBuchstamm = hoererBuchstammDAO.findByHoerernummer(hoerernummer);
        }
        return hoererBuchstamm;
    }

    public List<Belastung> findBelastungenBooksByTitleOrDatum(final String titel, final Date datum) {
        return hoererBuchstammDAO.findBelastungenBooksByTitleOrDatum(hoerernummer, titel, datum);
    }

    public HoererKennzeichen getHoererKennzeichen() {
        if (null == hoererKennzeichen) {
            hoererKennzeichen = hoererKennzeichenDAO.findByHoerernummer(hoerernummer);
        }
        return hoererKennzeichen;
    }

    public long getAktuelleBestellkarteCount() {
        return bestellkarteDAO.countBooksByHoerernummer(hoerernummer);
    }

    public Bestellkarte getBestellkarte() {
        if (null == bestellkarte) {
            bestellkarte = bestellkarteDAO.findByHoerernummer(hoerernummer);
        }
        return bestellkarte;
    }

    public List<Book> findAktuelleBestellkarteBooksByTitel(final String titel) {
        return bestellkarteDAO.findBooksByTitel(hoerernummer, titel);
    }

    public List<BestellkarteArchiv> getBestellkarteArchiv() {
        if (null == bestellkarteArchiv) {
            bestellkarteArchiv = bestellkarteArchivDAO.findByHoerernummer(hoerernummer);
        }
        return bestellkarteArchiv;
    }

    public List<BestellkarteArchiv> findBestellkarteArchivByTitelOrDatum(final String title, final Date datum) {
        bestellkarteArchiv = bestellkarteArchivDAO.findByTitleOrDatum(hoerernummer, title, datum);
        return bestellkarteArchiv;
    }

}
