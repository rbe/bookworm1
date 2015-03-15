/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.persistence;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.book.Book;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class BookDAO extends GenericDAO<Book> implements Serializable {

    public BookDAO() {
        super(Book.class);
    }

    public Book findByTitlenummer(final String titelnummer) {
        final Map<String, Object> map = new HashMap<>();
        map.put("titelnummer", titelnummer);
        final List<Book> books = dynamicFindWith(map, "");
        return books.size() > 0 ? books.get(0) : new Book();
    }

}
