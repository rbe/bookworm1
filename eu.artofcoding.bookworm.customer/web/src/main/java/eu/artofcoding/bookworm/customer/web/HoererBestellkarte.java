/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.Bestellkarte;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class HoererBestellkarte extends AbstractHoererBean {

    private Bestellkarte bestellkarte;

    private List<Book> books;

    public Date getDatumStand() {
        final Bestellkarte bestellkarte = getBestellkarte();
        if (null != bestellkarte) {
            return bestellkarte.getDatumStand();
        } else {
            return null;
        }
    }

    public long getAktuelleBestellkarteCount() {
        return hoererSession.getMyData().getBestellkarteBookCount();
    }

    private Bestellkarte getBestellkarte() {
        if (null == bestellkarte) {
            bestellkarte = hoererSession.getMyData().getBestellkarte();
            // TODO Fix ManyToMany/NamedQuery? books = bestellkarte.getBooks();
            searchTitle = "";
            search(null);
        }
        return bestellkarte;
    }

    public boolean hasBooks() {
        return null != books && books.size() > 0;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void search(ActionEvent e) {
        final String titel = String.format("%%%s%%", searchTitle);
        books = hoererSession.getMyData().findBestellkarteBooksByTitel(titel);
    }

}
