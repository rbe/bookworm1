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
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
            getBlistaOrders();
        }
        return null != blistaOrders && blistaOrders.size() > 0;
    }

    public List<BlistaOrder> getBlistaOrders() {
        if (null == blistaOrders) {
            search(null);
            filterBlistaOrders();
        }
        return blistaOrders;
    }

    private void filterBlistaOrders() {
        Iterator<BlistaOrder> blistaOrderIterator = blistaOrders.iterator();
        while (blistaOrderIterator.hasNext()) {
            final BlistaOrder blistaOrder = blistaOrderIterator.next();
            Iterator<Book> bookIterator = blistaOrder.getBooks().iterator();
            while (bookIterator.hasNext()) {
                final Book book = bookIterator.next();
                final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
                if (null != bookOrderStatus) {
                    final ChronoLocalDateTime<?> tenDaysBefore = ChronoLocalDateTime.from(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
                    final boolean isTenDaysBefore =
                                    null != bookOrderStatus.getRueckgabedatum() &&
                                    bookOrderStatus.getRueckgabedatum().isBefore(tenDaysBefore);
                    if (isTenDaysBefore) {
                        bookIterator.remove();
                    }
                } else {
                    bookIterator.remove();
                }
            }
            if (blistaOrder.getBooks().isEmpty()) {
                blistaOrderIterator.remove();
            }
        }
    }

    private BookOrderStatus getBookOrderStatus(final BlistaOrder blistaOrder, final Book book) {
        final String key = String.format("%s-%s", blistaOrder.getId(), book.getId());
        if (bookOrderStatuses.containsKey(key)) {
            return bookOrderStatuses.get(key);
        } else {
            try {
                final BookOrderStatus bookOrderStatus =
                        blistaRestClient.bookStatus(blistaOrder.getUserId(), book.getAghNummer());
                bookOrderStatuses.put(key, bookOrderStatus);
                return bookOrderStatus;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public boolean isDownloadable(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            final int available = bookOrderStatus.getMaxDownload() - bookOrderStatus.getDownloadCount();
            return bookOrderStatus.isBezugsfaehig() && available > 0;
        } else {
            return false;
        }
    }

    public String getStatusText(final BlistaOrder blistaOrder, final Book book) {
        final BookOrderStatus bookOrderStatus = getBookOrderStatus(blistaOrder, book);
        if (null != bookOrderStatus) {
            String status = "";
            switch (bookOrderStatus.getAusleihstatus()) {
                /*
                case 1:
                    status = "1: Noch nicht bearbeitet";
                    break;
                case 2:
                    status = "2: In Arbeit";
                    break;
                case 3:
                    status = "3: Buch steht zum Download bereit";
                    break;
                */
                case 4: // Korrektur des blista Services (DlsDescription)
                    status = "4: Downloadzeitraum abgelaufen";
                    break;
                default:
                    status = String.format("%s: %s", bookOrderStatus.getAusleihstatus(), bookOrderStatus.getDlsDescription());
            }
            return status;
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
