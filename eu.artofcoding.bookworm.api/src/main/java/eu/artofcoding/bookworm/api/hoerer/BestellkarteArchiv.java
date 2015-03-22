/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.hoerer;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.api.book.Book;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class BestellkarteArchiv implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    @NotNull
    private String hoerernummer;

    @OneToOne
    @NotNull
    private Book buch;

    @Basic
    @Column
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date ausleihdatum;

    @Basic
    @Column
    private String kennzeichen;

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

    public Date getAusleihdatum() {
        return ausleihdatum;
    }

    public void setAusleihdatum(Date datumStand) {
        this.ausleihdatum = datumStand;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public Book getBuch() {
        return buch;
    }

    public void setBuch(Book titelnummer) {
        this.buch = titelnummer;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

}
