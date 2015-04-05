/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.hoerer;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.crypt.CryptorEntityListener;
import eu.artofcoding.bookworm.common.crypt.TransparentCrypt;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@EntityListeners({CryptorEntityListener.class})
@NamedQueries({
        @NamedQuery(name = "Hoererstamm.findByHoerernummer", query = "SELECT o FROM Hoererstamm o WHERE o.hoerernummer = :hoerernummer")
})
public class Hoererstamm implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column(nullable = false)
    @Size(min = 1, max = 5)
    private String hoerernummer;

    @Basic
    @Column(nullable = false)
    @Size(min = 1, max = 15)
    private String anrede;

    @Basic
    @Column(nullable = false)
    //@Size(min = 1, max = 20)
    @TransparentCrypt
    private String vorname;

    @Basic
    @Column(nullable = false)
    //@Size(min = 1, max = 30)
    @TransparentCrypt
    private String nachname;

    @Basic
    @Column
    //@Size(min = 1, max = 30)
    @TransparentCrypt
    private String name2;

    @Basic
    @Column(nullable = false)
    //@Size(min = 1, max = 30)
    @TransparentCrypt
    private String strasse;

    @Basic
    @Column(nullable = true)
    //@Size(min = 0, max = 4)
    @TransparentCrypt
    private String land;

    @Basic
    @Column(nullable = false)
    //@Size(min = 1, max = 9)
    @TransparentCrypt
    private String plz;

    @Basic
    @Column(nullable = false)
    ////@Size(min = 1, max = 20)
    @TransparentCrypt
    private String ort;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date urlaubVon;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date urlaubBis;

    @Basic
    @Column(nullable = true)
    //@Size(min = 1, max = 30)
    @TransparentCrypt
    private String urlaubName2;

    @Basic
    @Column(nullable = true)
    ///@Size(min = 1, max = 30)
    @TransparentCrypt
    private String urlaubStrasse;

    @Basic
    @Column(nullable = true)
    //@Size(min = 0, max = 4)
    @TransparentCrypt
    private String urlaubLand;

    @Basic
    @Column(nullable = true)
    //@Size(min = 1, max = 9)
    @TransparentCrypt
    private String urlaubPlz;

    @Basic
    @Column(nullable = true)
    //@Size(min = 1, max = 20)
    @TransparentCrypt
    private String urlaubOrt;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date geburtsdatum;

    @Basic
    @Column
    //@Size(min = 1, max = 20)
    @TransparentCrypt
    private String telefonnummer;

    @Basic
    @Column
    @Size(min = 1, max = 1)
    private String urlaubKennzeichen;

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

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setUrlaubKennzeichen(String urlaubKennzeichen) {
        this.urlaubKennzeichen = urlaubKennzeichen;
    }

    public String getUrlaubKennzeichen() {
        return urlaubKennzeichen;
    }

    public Date getUrlaubBis() {
        return urlaubBis;
    }

    public void setUrlaubBis(Date urlaubBis) {
        this.urlaubBis = urlaubBis;
    }

    public Date getUrlaubVon() {
        return urlaubVon;
    }

    public void setUrlaubVon(Date urlaubVon) {
        this.urlaubVon = urlaubVon;
    }

    public String getUrlaubName2() {
        return urlaubName2;
    }

    public void setUrlaubName2(String urlaubName2) {
        this.urlaubName2 = urlaubName2;
    }

    public String getUrlaubLand() {
        return urlaubLand;
    }

    public void setUrlaubLand(String urlaubLand) {
        this.urlaubLand = urlaubLand;
    }

    public String getUrlaubOrt() {
        return urlaubOrt;
    }

    public void setUrlaubOrt(String urlaubOrt) {
        this.urlaubOrt = urlaubOrt;
    }

    public String getUrlaubPlz() {
        return urlaubPlz;
    }

    public void setUrlaubPlz(String urlaubPlz) {
        this.urlaubPlz = urlaubPlz;
    }

    public String getUrlaubStrasse() {
        return urlaubStrasse;
    }

    public void setUrlaubStrasse(String urlaubStrasse) {
        this.urlaubStrasse = urlaubStrasse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

}
