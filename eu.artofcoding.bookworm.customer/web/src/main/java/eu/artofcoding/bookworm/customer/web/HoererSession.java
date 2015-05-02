/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.beans.NoHoerernummerException;
import eu.artofcoding.bookworm.customer.web.persistence.BestellkarteArchivDAO;
import eu.artofcoding.bookworm.customer.web.persistence.BestellkarteDAO;
import eu.artofcoding.bookworm.customer.web.persistence.HoererBuchstammDAO;
import eu.artofcoding.bookworm.customer.web.qualifier.HoererCount;
import eu.artofcoding.bookworm.customer.web.qualifier.HoererValue;
import eu.artofcoding.bookworm.customer.web.qualifier.Hoerernummer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
public class HoererSession implements Serializable {

    //<editor-fold desc="Member">

    @Inject
    private HoererBuchstammDAO hoererBuchstammDAO;

    @Inject
    private BestellkarteDAO bestellkarteDAO;

    @Inject
    private BestellkarteArchivDAO bestellkarteArchivDAO;

    @Inject
    @Hoerernummer
    private String hoerernummer;

    @Inject
    @HoererValue
    private Hoererstamm hoererstamm;

    @Inject
    @HoererValue
    private HoererKennzeichen hoererKennzeichen;

    @Inject
    @HoererValue
    private HoererBuchstamm hoererBuchstamm;

    @Inject
    @HoererValue
    private Bestellkarte bestellkarte;

    @Inject @HoererCount(query = "Bestellkarte.Books")
    private long bestellkarteBooksCount;

    @Inject
    @HoererValue
    private ArrayList<BestellkarteArchiv> bestellkarteArchiv;

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

    public Hoererstamm getHoererstamm() {
        return hoererstamm;
    }

    public String getHoerernummer() {
        return getHoererstamm().getHoerernummer();
    }

    public String getNachname() {
        return getHoererstamm().getNachname();
    }

    public String getVorname() {
        return getHoererstamm().getVorname();
    }

    //</editor-fold>

    //<editor-fold desc="HoererBuchstamm">

    public HoererBuchstamm getHoererBuchstamm() {
        return hoererBuchstamm;
    }

    public List<Belastung> findBelastungenBooksByTitleOrDatum(final String titel, final Date datum) {
        return hoererBuchstammDAO.findBelastungenBooksByTitleOrDatum(hoerernummer, titel, datum);
    }

    //</editor-fold>

    public HoererKennzeichen getHoererKennzeichen() {
        return hoererKennzeichen;
    }

    public long getBestellkarteBookCount() {
        return bestellkarteBooksCount;
    }

    public Bestellkarte getBestellkarte() {
        return bestellkarte;
    }

    public List<Book> findBestellkarteBooksByTitel(final String titel) {
        return bestellkarteDAO.findBooksByTitel(hoerernummer, titel);
    }

    public List<BestellkarteArchiv> getBestellkarteArchiv() {
        return bestellkarteArchiv;
    }

    public List<BestellkarteArchiv> findBestellkarteArchivByTitelOrDatum(final String title, final Date datum) {
        return bestellkarteArchivDAO.findByTitleOrDatum(hoerernummer, title, datum);
    }

}
