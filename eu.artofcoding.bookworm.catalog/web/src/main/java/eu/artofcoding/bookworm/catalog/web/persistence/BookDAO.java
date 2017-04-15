/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 */

package eu.artofcoding.bookworm.catalog.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Named
public class BookDAO extends GenericDAO<Book> implements Serializable {

    @PersistenceContext(name = "bookwormPU")
    private transient EntityManager em;

    public BookDAO() {
        super(Book.class);
    }

    @PostConstruct
    private void postConstruct() {
        setEntityManager(em);
    }

}
