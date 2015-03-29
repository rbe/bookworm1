/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.api.book.Book;
import eu.artofcoding.bookworm.api.hoerer.Bestellkarte;

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

    public String getHoerernummer() {
        return hoererSession.getMyData().getHoerernummer();
    }

    public String getVorname() {
        return hoererSession.getMyData().getVorname();
    }

    public String getNachname() {
        return hoererSession.getMyData().getNachname();
    }

    public Date getDatumStand() {
        return getBestellkarte().getDatumStand();
    }

    public long getAktuelleBestellkarteCount() {
        return hoererSession.getMyData().getAktuelleBestellkarteCount();
    }

    public Bestellkarte getBestellkarte() {
        if (null == bestellkarte) {
            bestellkarte = hoererSession.getMyData().getBestellkarte();
            books = bestellkarte.getBooks();
        }
        return bestellkarte;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void search(ActionEvent e) {
        final String titel = String.format("%%%s%%", searchTitle);
        books = hoererSession.getMyData().findAktuelleBestellkarteBooksByTitel(titel);
    }

}
