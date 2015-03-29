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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Bestellkarte.countByHoerernummer", query = "SELECT COUNT(o.books) FROM Bestellkarte o WHERE o.hoerernummer = :hoerernummer"),
        @NamedQuery(name = "Bestellkarte.findByHoerernummer", query = "SELECT o FROM Bestellkarte o WHERE o.hoerernummer = :hoerernummer"),
        @NamedQuery(name = "Bestellkarte.findBooksByTitel", query = "SELECT b FROM Bestellkarte o INNER JOIN o.books b WHERE b.titel LIKE :titel")
})
public class Bestellkarte implements GenericEntity {

    @Basic
    @Column
    private Long id;

    @Version
    private Long version;

    @Id
    @Column
    @Size(min = 1, max = 5)
    @NotNull
    private String hoerernummer;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> books;

    @Basic
    @Column
    @Temporal(value = TemporalType.DATE)
    private Date datumStand;

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

    public Date getDatumStand() {
        return datumStand;
    }

    public void setDatumStand(Date datumStand) {
        this.datumStand = datumStand;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public List<Book> getBooks() {
        if (null == books) {
            books = new LinkedList<>();
        }
        return books;
    }

    public void setBooks(List<Book> buch) {
        this.books = buch;
    }

    public void addBook(Book book) {
        getBooks().add(book);
    }

}
