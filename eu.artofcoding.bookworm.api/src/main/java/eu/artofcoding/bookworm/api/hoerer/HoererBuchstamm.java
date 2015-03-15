/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.hoerer;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.api.book.Sachgebiet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class HoererBuchstamm implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    @NotNull
    @Size(min = 1, max = 5)
    private String hoerernummer;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date anmeldedatum;

    @Basic
    @Column
    @DecimalMax("9")
    private Integer mengenindex;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date sperrTerminVon;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date sperrTerminBis;

    @Basic
    @Column
    @Size(min = 1, max = 1)
    private String sperrKz;

    @Basic
    @Column
    @Size(min = 1, max = 1)
    private String bestellkkz;

    @OneToMany
    private Set<Sachgebiet> sachgebiet;

    @Basic
    @Column
    @DecimalMax("9")
    private Integer rueckbuchungskz;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date rueckbuchungsdatum;

    @Basic
    @Column
    private String loeschkennzeichen;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Belastung> belastungen = new LinkedList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getAnmeldedatum() {
        return anmeldedatum;
    }

    public void setAnmeldedatum(Date anmeldedatum) {
        this.anmeldedatum = anmeldedatum;
    }

    public Integer getMengenindex() {
        return mengenindex;
    }

    public void setMengenindex(Integer mengenindex) {
        this.mengenindex = mengenindex;
    }

    public String getBestellkkz() {
        return bestellkkz;
    }

    public void setBestellkkz(String bestellkkz) {
        this.bestellkkz = bestellkkz;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public Integer getRueckbuchungskz() {
        return rueckbuchungskz;
    }

    public void setRueckbuchungskz(Integer rueckbuchungskz) {
        this.rueckbuchungskz = rueckbuchungskz;
    }

    public Set<Sachgebiet> getSachgebiet() {
        return sachgebiet;
    }

    public void setSachgebiet(Set<Sachgebiet> sachgebiet) {
        this.sachgebiet = sachgebiet;
    }

    public String getSperrKz() {
        return sperrKz;
    }

    public void setSperrKz(String sperrKz) {
        this.sperrKz = sperrKz;
    }

    public Date getSperrTerminBis() {
        return sperrTerminBis;
    }

    public void setSperrTerminBis(Date terminBis) {
        this.sperrTerminBis = terminBis;
    }

    public Date getSperrTerminVon() {
        return sperrTerminVon;
    }

    public void setSperrTerminVon(Date terminVon) {
        this.sperrTerminVon = terminVon;
    }

    public Date getRueckbuchungsdatum() {
        return rueckbuchungsdatum;
    }

    public void setRueckbuchungsdatum(Date rueckbungsdatum) {
        this.rueckbuchungsdatum = rueckbungsdatum;
    }

    public String getLoeschkennzeichen() {
        return loeschkennzeichen;
    }

    public void setLoeschkennzeichen(String loeschkennzeichen) {
        this.loeschkennzeichen = loeschkennzeichen;
    }

    public List<Belastung> getBelastungen() {
        return belastungen;
    }

    public void setBelastungen(List<Belastung> belastungen) {
        this.belastungen = belastungen;
    }

    public Belastung addBelastung(final int index, final String titelnummer, final String boxnummer) {
        final Belastung belastung = new Belastung();
        belastung.setBelastungIndex(index);
        belastung.setTitelnummer(titelnummer);
        belastung.setBoxnummer(boxnummer);
        belastungen.add(belastung);
        return belastung;
    }

    public Belastung getBelastung(final int index) {
        Belastung belastung = null;
        for (Belastung b : belastungen) {
            if (b.getBelastungIndex() == index) {
                belastung = b;
                break;
            }
        }
        if (null == belastung) {
            throw new RuntimeException("Cannot find Belastung with index " + index);
        }
        return belastung;
    }

}
