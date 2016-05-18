/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.basket;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import org.apache.openjpa.persistence.jdbc.ForeignKey;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NamedQueries({
        @NamedQuery(name = "BlistaOrder.countByHoerernummer", query = "SELECT COUNT(o) FROM BlistaOrder o WHERE o.hoerernummer = :hoerernummer"),
        @NamedQuery(name = "BlistaOrder.findByHoerernummerOrderByAusleihdatum", query = "SELECT o FROM BlistaOrder o WHERE o.hoerernummer = :hoerernummer ORDER BY o.ausleihdatum ASC"),
        @NamedQuery(name = "BlistaOrder.findByHoerernummerAndTitelOrderByAusleihdatum", query = "SELECT o FROM BlistaOrder o LEFT OUTER JOIN o.books b WHERE o.hoerernummer = :hoerernummer AND b.titel LIKE :titel ORDER BY o.ausleihdatum ASC"),
        @NamedQuery(name = "BlistaOrder.findByHoerernummerAndTitelAndAusleihdatumOrderByAusleihdatum", query = "SELECT o FROM BlistaOrder o WHERE o.hoerernummer = :hoerernummer AND o.buch.titel LIKE :titel AND o.ausleihdatum >= :datum ORDER BY o.ausleihdatum ASC")
})
public class BlistaOrder implements GenericEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Basic
    private String hoerernummer;

    @Basic
    private String userId;

    @Basic
    private String email;

    @Basic
    private Date ausleihdatum;

    @OneToMany(fetch = FetchType.EAGER)
    @ForeignKey(enabled = false)
    private List<Book> books = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Map<AghNummer, Abrufkennwort> aghAbrufkennwortMap = new HashMap<>();

    @PrePersist
    void prePersist() {
        ausleihdatum = new Date();
    }

    private String normalizeUserId(final String hoerernummer, final String userId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(hoerernummer);
        final String u = userId.chars()
                .filter(i -> i > 32 && i < 127)
                .mapToObj(i -> (char) i)
                .collect(() -> builder, (sb, c) -> sb.append((char) c), StringBuilder::append)
                .toString();
        return u.length() > 30 ? u.substring(1, 30) : u;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(final String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = normalizeUserId(hoerernummer, userId);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getAusleihdatum() {
        return ausleihdatum;
    }

    public void setAusleihdatum(final Date ausleihdatum) {
        this.ausleihdatum = ausleihdatum;
    }

    public List<Book> getBooks() {
        if (null == books) {
            books = new ArrayList<>();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Map<AghNummer, Abrufkennwort> getAghAbrufkennwortMap() {
        return aghAbrufkennwortMap;
    }

    public void setAghAbrufkennwortMap(final Map<AghNummer, Abrufkennwort> aghAbrufkennwortMap) {
        this.aghAbrufkennwortMap = aghAbrufkennwortMap;
    }

    public void abrufkennwort(final String aghNummer, final String abrufkenntwort) {
        aghAbrufkennwortMap.put(new AghNummer(aghNummer), new Abrufkennwort(abrufkenntwort));
    }

}
