/*
 * bookworm
 * bookworm-web
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu/
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 29.08.12 11:15
 */

package eu.artofcoding.bookworm.web;

import eu.artofcoding.beetlejuice.persistence.GenericDAO;
import eu.artofcoding.bookworm.api.BookEntity;

import javax.ejb.Stateful;
import java.io.Serializable;

@Stateful
public class BookDAO extends GenericDAO<BookEntity> implements Serializable {

    public BookDAO() {
        super(BookEntity.class);
    }

}
