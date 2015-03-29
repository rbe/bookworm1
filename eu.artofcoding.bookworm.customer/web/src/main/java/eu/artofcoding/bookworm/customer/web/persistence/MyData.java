/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.bookworm.api.book.Book;
import eu.artofcoding.bookworm.api.book.Sachgebiet;
import eu.artofcoding.bookworm.api.hoerer.Bestellkarte;
import eu.artofcoding.bookworm.api.hoerer.Belastung;
import eu.artofcoding.bookworm.api.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.api.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.api.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;

import javax.ejb.EJB;
import javax.ejb.Stateful;
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

    private String hoerernummer;

    private Hoererstamm hoererstamm;

    private HoererKennzeichen hoererKennzeichen;

    private HoererBuchstamm hoererBuchstamm;

    private Bestellkarte bestellkarte;

    private List<BestellkarteArchiv> bestellkarteArchiv;

    //</editor-fold>

    public void init(final String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

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

    public List<Book> findAktuelleBestellkarteBooksByTitel(String titel) {
        return bestellkarteDAO.findBookByTitel(titel);
    }

    public List<BestellkarteArchiv> getBestellkarteArchiv() {
        if (null == bestellkarteArchiv) {
            bestellkarteArchiv = bestellkarteArchivDAO.findByHoerernummer(hoerernummer);
        }
        return bestellkarteArchiv;
    }

    public List<BestellkarteArchiv> findBestellkarteArchivByTitelOrDatum(String title, Date datum) {
        bestellkarteArchiv = bestellkarteArchivDAO.findByTitleOrDatum(hoerernummer, title, datum);
        return bestellkarteArchiv;
    }

    public List<Belastung> findBelastungenBooksByTitleOrDatum(String titel, Date datum) {
        return hoererBuchstammDAO.findBelastungenBooksByTitleOrDatum(hoerernummer, titel, datum);
    }

}
