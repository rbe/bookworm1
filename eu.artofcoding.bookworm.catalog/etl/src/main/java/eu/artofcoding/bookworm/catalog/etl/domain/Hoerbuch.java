/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Entity
 */
public final class Hoerbuch implements Serializable {

    private static final long serialVersionUID = -1L;

    private Sachgebiet sachgebiet;

    private Titelnummer titelnummer; // 6 0 (nummerisch)

    private AghNummer aghNummer;

    private String autor;

    private String titel;

    private String untertitel;

    private String erlaeuterung;

    private String verlagsort;

    private String verlag;

    private String druckjahr;

    private String sprecher1;

    private String sprecher2;

    private String spieldauer; // 52 Stunde, Minuten

    private String prodOrt;

    private String prodJahr;

    private String suchwoerter;

    private String anzahlCD;

    private String titelfamilie;

    private Date einstelldatum;

    private boolean downloadbar;

    public Sachgebiet getSachgebiet() {
        return sachgebiet;
    }

    public void setSachgebiet(Sachgebiet sachgebiet) {
        this.sachgebiet = sachgebiet;
    }

    void setSachgebiet(char sachgebiet) {
        this.sachgebiet = Sachgebiet.valueOf(String.valueOf(sachgebiet));
    }

    public Titelnummer getTitelnummer() {
        return titelnummer;
    }

    public void setTitelnummer(Titelnummer titelnummer) {
        this.titelnummer = titelnummer;
    }

    void setTitelnummer(String titelnummer) {
        this.titelnummer = new Titelnummer(titelnummer);
    }

    public AghNummer getAghNummer() {
        return aghNummer;
    }

    public void setAghNummer(final AghNummer aghNummer) {
        this.aghNummer = aghNummer;
    }

    void setAghNummer(final String aghNummer) {
        this.aghNummer = new AghNummer(aghNummer);
    }

    public String getAutor() {
        return autor;
    }

    void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitel() {
        return titel;
    }

    void setTitel(String titel) {
        this.titel = titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public String getErlaeuterung() {
        return erlaeuterung;
    }

    void setErlaeuterung(String erlaeuterung) {
        this.erlaeuterung = erlaeuterung;
    }

    public String getVerlagsort() {
        return verlagsort;
    }

    void setVerlagsort(String verlagsort) {
        this.verlagsort = verlagsort;
    }

    public String getVerlag() {
        return verlag;
    }

    void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    public String getDruckjahr() {
        return druckjahr;
    }

    void setDruckjahr(String druckjahr) {
        this.druckjahr = druckjahr;
    }

    public String getSprecher1() {
        return sprecher1;
    }

    void setSprecher1(String sprecher1) {
        this.sprecher1 = sprecher1;
    }

    public String getSprecher2() {
        return sprecher2;
    }

    void setSprecher2(String sprecher2) {
        this.sprecher2 = sprecher2;
    }

    public String getSpieldauer() {
        String _spieldauer = null;
        if (null != spieldauer) {
            String[] parts = spieldauer.split(",");
            if (!"00".equals(parts[1])) {
                _spieldauer = String.format("%s Stunden %s Minuten", parts[0], parts[1]);
            } else {
                _spieldauer = String.format("%s Stunden", parts[0]);
            }
        }
        return _spieldauer;
    }

    void setSpieldauer(String spieldauer) {
        this.spieldauer = spieldauer;
    }

    public String getProdOrt() {
        return prodOrt;
    }

    void setProdOrt(String prodOrt) {
        this.prodOrt = prodOrt;
    }

    public String getProdJahr() {
        return prodJahr;
    }

    void setProdJahr(String prodJahr) {
        this.prodJahr = prodJahr;
    }

    public String getSuchwoerter() {
        return suchwoerter;
    }

    void setSuchwoerter(String suchwoerter) {
        this.suchwoerter = suchwoerter;
    }

    public String getAnzahlCD() {
        return anzahlCD;
    }

    void setAnzahlCD(String anzahlCD) {
        this.anzahlCD = anzahlCD;
    }

    public String getTitelfamilie() {
        return titelfamilie;
    }

    void setTitelfamilie(String titelfamilie) {
        this.titelfamilie = titelfamilie;
    }

    public Date getEinstelldatum() {
        return einstelldatum;
    }

    void setEinstelldatum(Date einstelldatum) {
        this.einstelldatum = einstelldatum;
    }

    public boolean isDownloadbar() {
        return downloadbar;
    }

    void setDownloadbar(final boolean downloadbar) {
        this.downloadbar = downloadbar;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Hoerbuch hoerbuch = (Hoerbuch) o;
        return Objects.equals(titelnummer, hoerbuch.titelnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titelnummer);
    }

    @Override
    public String toString() {
        return String.format("BookEntity{titelnummer='%s', aghNummer='%s', sachgebiet='%s', autor='%s', titel='%s'}",
                titelnummer, aghNummer, sachgebiet, autor, titel);
    }

}
