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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;

@Entity
public class Belastung implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    private int belastungIndex;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date datum;

    @Basic
    @Column
    private String boxnummer;

    @OneToOne
    @ForeignKey(enabled = false)
    private Book book;

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

    public int getBelastungIndex() {
        return belastungIndex;
    }

    public void setBelastungIndex(int index) {
        this.belastungIndex = index;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBoxnummer() {
        return boxnummer;
    }

    public void setBoxnummer(String boxnummer) {
        this.boxnummer = boxnummer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Belastung)) return false;
        Belastung belastung = (Belastung) o;
        if (belastungIndex != belastung.belastungIndex) return false;
        if (!boxnummer.equals(belastung.boxnummer)) return false;
        if (!datum.equals(belastung.datum)) return false;
        if (!book.getTitelnummer().equals(belastung.getBook().getTitelnummer())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = belastungIndex;
        if (null != datum) {
            result = 31 * result + datum.hashCode();
        }
        result = 31 * result + book.hashCode();
        if (null != boxnummer) {
            result = 31 * result + boxnummer.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Belastung{belastungIndex=%d, boxnummer='%s', book=%s}", belastungIndex, boxnummer, book);
    }

}
