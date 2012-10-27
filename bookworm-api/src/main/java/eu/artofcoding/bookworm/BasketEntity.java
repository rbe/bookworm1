/*
 * bookworm
 * bookworm-api
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu/
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 26.10.12 16:18
 */

package eu.artofcoding.bookworm;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "basket")
@NamedQueries({
})
public class BasketEntity implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Version
    public Long version;

    @OneToMany
    private List<BookEntity> books;

    @Basic
    @Column(length = 100)
    private String name;

    @Basic
    @Column(length = 10)
    private String hoerernummer;

    @Basic
    @Column(length = 150)
    private String email;

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

    public List<BookEntity> getBooks() {
        if (null == books) {
            books = new ArrayList<BookEntity>();
        }
        return books;
    }

    public boolean isInBasket(Long id) {
        boolean b = false;
        for (BookEntity book : getBooks()) {
            if (book.getId() == id) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
