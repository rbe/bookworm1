/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import java.io.Serializable;
import java.util.List;

public interface BasketBean extends Serializable {

    String getName();

    Basket getBasket();

    List<Book> getBooks();

    boolean isEmpty();

    int getItemCount();

    boolean isInBasket(Book book);

    String add(Book book);

    String remove(Book book);

    void wasOrdered();

}
