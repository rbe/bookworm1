/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.hoerer;

import eu.artofcoding.bookworm.api.persistence.SqlStatementCapable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class BestellkarteArchiv implements SqlStatementCapable {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    @Column
    private String hoerernummer;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date ausleihdatum;

    @Basic @Column
    private String titelnummer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitelnummer() {
        return titelnummer;
    }

    public void setTitelnummer(String titelnummer) {
        this.titelnummer = titelnummer;
    }

    @Override
    public String toInsertStatement() {
        return "";
    }

}
