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
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Hoererstamm implements SqlStatementCapable {

    private static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    @Column
    private String hoerernummer;

    @Basic
    @Column
    private String anrede;

    @Basic
    @Column
    private String vorname;

    @Basic
    @Column
    private String nachname;

    @Basic
    @Column
    private String name2;

    @Basic
    @Column
    private String strasse;

    @Basic
    @Column
    private String plz;

    @Basic
    @Column
    private String ort;

    @Basic
    @Column
    private Date geburtsdatum;

    @Basic
    @Column
    private String telefonnummer;

    @Basic
    @Column
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
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

    @Override
    public String toInsertStatement() {
        String g = "null";
        if (null != geburtsdatum) {
            g = String.format("'%s'", ISO_DATE_FORMAT.format(geburtsdatum));
        }
        return String.format("INSERT INTO hoererstamm" +
                " (hoerernummer, anrede, vorname, nachname, name2, geburtsdatum, strasse, plz, ort, telefonnummer)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', %s, '%s', '%s', '%s', '%s');", hoerernummer, anrede, vorname, nachname, name2, g, strasse, plz, ort, telefonnummer);
    }

}
