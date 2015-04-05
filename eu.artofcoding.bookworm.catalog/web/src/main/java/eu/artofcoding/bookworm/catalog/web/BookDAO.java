/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
public class BookDAO extends GenericDAO<Book> implements Serializable {

    public BookDAO() {
        super(Book.class);
    }

}
