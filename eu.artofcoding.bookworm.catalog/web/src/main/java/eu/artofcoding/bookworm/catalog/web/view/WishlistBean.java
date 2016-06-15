/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.catalog.web.persistence.WishlistDAO;
import eu.artofcoding.bookworm.common.persistence.basket.Wishlist;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static eu.artofcoding.bookworm.catalog.web.persistence.HoerernummerFilter.getHoerernummer;

@Named
@SessionScoped
public class WishlistBean implements Serializable {

    @Inject
    @PostalOrder
    private BasketBean postalBasketBean;

    @Inject
    @DigitalOrder
    private BasketBean digitalBasketBean;

    @Inject
    private WishlistDAO wishlistDAO;

    private Wishlist wishlist;

    @PostConstruct
    private void postConstruct() {
        final String hoerernummer = getHoerernummer();
        final Map<String, Object> p = new HashMap<String, Object>() {{
            put("hoerernummer", hoerernummer);
        }};
        final List<Wishlist> all = wishlistDAO.findAll("Wishlist.findByHoerernummer", p);
        if (all.size() == 1) {
            wishlist = all.get(0);
        } else {
            wishlist = new Wishlist();
            wishlist.setHoerernummer(hoerernummer);
        }
    }

    public String add(final Book book) {
        wishlist.getBooks().add(book);
        wishlist = wishlistDAO.update(wishlist);
        return null;
    }

    public String moveToPostalBasketBean(final Book book) {
        postalBasketBean.add(book);
        remove(book);
        return null;
    }

    public String moveToDigitalBasketBean(final Book book) {
        digitalBasketBean.add(book);
        remove(book);
        return null;
    }

    public void remove(final Book book) {
        if (null != book) {
            for (Iterator<Book> iterator = wishlist.getBooks().iterator(); iterator.hasNext(); ) {
                Book b = iterator.next();
                if (b.getTitelnummer().equals(book.getTitelnummer())) {
                    iterator.remove();
                }
            }
            wishlist = wishlistDAO.update(wishlist);
            postConstruct();
        }
    }

    public int getItemCount() {
        return wishlist.itemCount();
    }

    public List<Book> getBooks() {
        return wishlist.getBooks();
    }

    public boolean isInWishlist(final Book book) {
        return wishlist.getBooks().stream().anyMatch(b -> b.getTitelnummer().equals(book.getTitelnummer()));
    }

}
