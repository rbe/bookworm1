/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.integration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BookOrderStatusFactory {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private BookOrderStatusFactory() {
        throw new AssertionError();
    }

    static BookOrderStatus from(final DlsBook dlsBook) {
        final BookOrderStatus bookOrderStatus = new BookOrderStatus();
        bookOrderStatus.setAghNummer(dlsBook.getBestellnummer());
        bookOrderStatus.setBestelldatum(LocalDateTime.parse(dlsBook.getBestelldatum(), DATE_TIME_FORMATTER));
        bookOrderStatus.setRueckgabedatum(LocalDateTime.parse(dlsBook.getRueckgabedatum(), DATE_TIME_FORMATTER));
        bookOrderStatus.setDownloadLink(dlsBook.getDownloadLink());
        bookOrderStatus.setDownloadCount(dlsBook.getDownloadCount());
        bookOrderStatus.setMaxDownload(dlsBook.getMaxDownload());
        bookOrderStatus.setAusleihstatus(dlsBook.getAusleihstatus());
        bookOrderStatus.setDlsDescription(dlsBook.getDlsDescription());
        switch (dlsBook.getAusleihstatus()) {
            case 0:
            case 1:
            case 2:
            case 4:
                bookOrderStatus.setBezugsfaehig(false);
                break;
            case 3:
                bookOrderStatus.setBezugsfaehig(true);
                break;
        }
        if (dlsBook.getAusleihstatus() == 0) {
            final String dlsDescription = dlsBook.getDlsDescription();
            if (null != dlsDescription && dlsDescription.indexOf("doppelt bestellt") > 0) {
                bookOrderStatus.setBezugsfaehig(true);
            }
        }
        return bookOrderStatus;
    }

}
