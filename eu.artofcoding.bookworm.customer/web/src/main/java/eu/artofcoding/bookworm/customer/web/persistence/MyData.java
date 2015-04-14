/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.book.Sachgebiet;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.Hoerernummer;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    /*
    @PrePassivate
    public void prePassivate() {
        System.out.println(this + ": prePassivate");
    }

    @PostActivate
    public void postActivate() {
        System.out.println(this + ": postActivate");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(this + ": postConstruct, hoerernummer=" + hoerernummer);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(this + ": preDestroy");
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        System.out.println(this + ": " + ctx.getMethod().getName() + " hoerernummer=" + hoerernummer);
        return ctx.proceed();
    }
    */

    //</editor-fold>

    //<editor-fold desc="Hoererstamm">

    public String getAnrede() {
        return getHoererstamm().getAnrede();
    }

    public Date getGeburtsdatum() {
        return getHoererstamm().getGeburtsdatum();
    }

    public String getHoerernummer() {
        return getHoererstamm().getHoerernummer();
    }

    public String getLand() {
        return getHoererstamm().getLand();
    }

    public String getNachname() {
        return getHoererstamm().getNachname();
    }

    public String getName2() {
        return getHoererstamm().getName2();
    }

    public String getOrt() {
        return getHoererstamm().getOrt();
    }

    public String getPlz() {
        return getHoererstamm().getPlz();
    }

    public String getStrasse() {
        return getHoererstamm().getStrasse();
    }

    public String getTelefonnummer() {
        return getHoererstamm().getTelefonnummer();
    }

    public Date getUrlaubBis() {
        return getHoererstamm().getUrlaubBis();
    }

    public String getUrlaubKennzeichen() {
        return getHoererstamm().getUrlaubKennzeichen();
    }

    public String getUrlaubLand() {
        return getHoererstamm().getUrlaubLand();
    }

    public String getUrlaubName2() {
        return getHoererstamm().getUrlaubName2();
    }

    public String getUrlaubOrt() {
        return getHoererstamm().getUrlaubOrt();
    }

    public String getUrlaubPlz() {
        return getHoererstamm().getUrlaubPlz();
    }

    public String getUrlaubStrasse() {
        return getHoererstamm().getUrlaubStrasse();
    }

    public Date getUrlaubVon() {
        return getHoererstamm().getUrlaubVon();
    }

    public String getVorname() {
        return getHoererstamm().getVorname();
    }

    //</editor-fold>

    public Hoererstamm getHoererstamm() {
        if (null == hoererstamm) {
            hoererstamm = hoererstammDAO.findByHoerernummer(hoerernummer);
        }
        return hoererstamm;
    }

    //<editor-fold desc="HoererBuchstamm">

    public Date getSperrTerminVon() {
        return getHoererBuchstamm().getSperrTerminVon();
    }

    public Date getAnmeldedatum() {
        return getHoererBuchstamm().getAnmeldedatum();
    }

    public Belastung getBelastung(int index) {
        return getHoererBuchstamm().getBelastung(index);
    }

    public List<Belastung> getBelastungen() {
        return getHoererBuchstamm().getBelastungen();
    }

    public String getBestellkkz() {
        return getHoererBuchstamm().getBestellkkz();
    }

    public String getLoeschkennzeichen() {
        return getHoererBuchstamm().getLoeschkennzeichen();
    }

    public Integer getMengenindex() {
        return getHoererBuchstamm().getMengenindex();
    }

    public Date getRueckbuchungsdatum() {
        return getHoererBuchstamm().getRueckbuchungsdatum();
    }

    public Integer getRueckbuchungskz() {
        return getHoererBuchstamm().getRueckbuchungskz();
    }

    public Set<Sachgebiet> getSachgebiet() {
        return getHoererBuchstamm().getSachgebiet();
    }

    public String getSperrKz() {
        return getHoererBuchstamm().getSperrKz();
    }

    public Date getSperrTerminBis() {
        return getHoererBuchstamm().getSperrTerminBis();
    }

    //</editor-fold>

    public HoererBuchstamm getHoererBuchstamm() {
        if (null == hoererBuchstamm) {
            hoererBuchstamm = hoererBuchstammDAO.findByHoerernummer(hoerernummer);
        }
        return hoererBuchstamm;
    }

    //<editor-fold desc="HoererKennzeichen">

    public String getEmail() {
        return getHoererKennzeichen().getEmail();
    }

    //</editor-fold>

    public HoererKennzeichen getHoererKennzeichen() {
        if (null == hoererKennzeichen) {
            hoererKennzeichen = hoererKennzeichenDAO.findByHoerernummer(hoerernummer);
        }
        return hoererKennzeichen;
    }

    public long getAktuelleBestellkarteCount() {
        return bestellkarteDAO.countByHoerernummer(hoerernummer);
    }

    public Bestellkarte getBestellkarte() {
        if (null == bestellkarte) {
            bestellkarte = bestellkarteDAO.findByHoerernummer(hoerernummer);
        }
        return bestellkarte;
    }

    public List<Book> findAktuelleBestellkarteBooksByTitel(final String titel) {
        return bestellkarteDAO.findBookByTitel(titel);
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

    public List<Belastung> findBelastungenBooksByTitleOrDatum(final String titel, final Date datum) {
        return hoererBuchstammDAO.findBelastungenBooksByTitleOrDatum(hoerernummer, titel, datum);
    }

}
