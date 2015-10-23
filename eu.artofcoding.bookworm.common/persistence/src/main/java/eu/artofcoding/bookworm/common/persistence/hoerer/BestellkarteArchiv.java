/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.hoerer;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import org.apache.openjpa.persistence.jdbc.ForeignKey;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "BestellkarteArchiv.countByHoerernummer", query = "SELECT COUNT(o) FROM BestellkarteArchiv o WHERE o.hoerernummer = :hoerernummer"),
        @NamedQuery(name = "BestellkarteArchiv.findByHoerernummerOrderByAusleihdatum", query = "SELECT o FROM BestellkarteArchiv o WHERE o.hoerernummer = :hoerernummer ORDER BY o.ausleihdatum ASC"),
        @NamedQuery(name = "BestellkarteArchiv.findByHoerernummerAndTitelOrderByAusleihdatum", query = "SELECT o FROM BestellkarteArchiv o WHERE o.hoerernummer = :hoerernummer AND o.buch.titel LIKE :titel ORDER BY o.ausleihdatum ASC"),
        @NamedQuery(name = "BestellkarteArchiv.findByHoerernummerAndTitelAndAusleihdatumOrderByAusleihdatum", query = "SELECT o FROM BestellkarteArchiv o WHERE o.hoerernummer = :hoerernummer AND o.buch.titel LIKE :titel AND o.ausleihdatum >= :datum ORDER BY o.ausleihdatum ASC")
})
public class BestellkarteArchiv implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    //@NotNull
    private String hoerernummer;

    @OneToOne
    //@NotNull
    @ForeignKey(enabled = false)
    private Book buch;

    @Basic
    @Column
    //@NotNull
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

    @Override
    public String toString() {
        return String.format("BestellkarteArchiv{hoerernummer='%s'}", hoerernummer);
    }

}
