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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Basket implements GenericEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @OneToMany
    @ForeignKey(enabled = false)
    private List<Book> books;

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

    public List<Book> getBooks() {
        if (null == books) {
            books = new ArrayList<>();
        }
        return books;
    }

    public int itemCount() {
        return getBooks().size();
    }

    public boolean isInBasket(final Book book) {
        boolean inBasket = false;
        for (Book b : getBooks()) {
            if (Objects.equals(book, b)) {
                inBasket = true;
                break;
            }
        }
        return inBasket;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
