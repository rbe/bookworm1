/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.view;

import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BookOrderStatus;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class HoererBlistaAusleihe extends AbstractHoererBean {

    @Inject
    private BlistaRestClient blistaRestClient;

    private List<BlistaOrder> blistaOrders;

    private Map<String, BookOrderStatus> bookOrderStatuses = new HashMap<>();

    public void search(final ActionEvent e) {
        final String titel = String.format("%%%s%%", searchTitle);
        Date datum = null;
        if (null != searchDate && searchDate.length() == 10) {
            datum = ParserHelper.parseGermanDate(searchDate);
        }
        blistaOrders = hoererSession.findBlistaOrderByTitleOrDatum(titel, datum);
    }

    public boolean hasBlistaOrders() {
        if (null == blistaOrders) {
            search(null);
        }
        return null != blistaOrders && blistaOrders.size() > 0;
    }

    public List<BlistaOrder> getBlistaOrders() {
        if (null == blistaOrders) {
            search(null);
        }
        return blistaOrders;
    }

    /*
    private BlistaOrder getBlistaOrder(final Long blistaOrderId) {
        final List<BlistaOrder> blistaOrders = this.blistaOrders.stream()
                .filter(o -> o.getId().equals(blistaOrderId))
                .collect(Collectors.toList());
        return blistaOrders.get(0);
    }

    private Book getBook(final Long bookId, final BlistaOrder blistaOrder) {
        final List<Book> books = blistaOrder
                .getBooks().stream().filter(b -> b.getId().equals(bookId))
                .collect(Collectors.toList());
        return books.get(0);
    }
    */

    private BookOrderStatus getBookOrderStatus(final BlistaOrder blistaOrder, final Book book) {
        final String key = String.format("%s-%s", blistaOrder.getId(), book.getId());
        if (bookOrderStatuses.containsKey(key)) {
            return bookOrderStatuses.get(key);
        } else {
            final BookOrderStatus bookOrderStatus = blistaRestClient.bookStatus(blistaOrder.getUserId(), book.getAghNummer());
            bookOrderStatuses.put(key, bookOrderStatus);
            return bookOrderStatus;
        }
    }

    public boolean isDownloadable(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            return bookOrderStatus.isBezugsfaehig();
        } else {
            return false;
        }
    }

    public String getStatusText(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            return String.format("%s: %s", bookOrderStatus.getAusleihstatus(), bookOrderStatus.getDlsDescription());
        } else {
            return "Leider kein Status verfügbar";
        }
    }

    public String getDownloadCount(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            final int available = bookOrderStatus.getMaxDownload() - bookOrderStatus.getDownloadCount();
            return String.format("%s / %s", available, bookOrderStatus.getMaxDownload());
        } else {
            return "? / ?";
        }
    }

    public String getDownloadLink(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            return bookOrderStatus.getDownloadLink();
        } else {
            return "#";
        }
    }

}
