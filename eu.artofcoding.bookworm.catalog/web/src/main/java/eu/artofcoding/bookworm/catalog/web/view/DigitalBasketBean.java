/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@DigitalOrder
@SessionScoped
public class DigitalBasketBean extends AbstractBasketBean {

    private final Map<String, Boolean> availableAsDownloadMap = new HashMap<>();

    @Inject
    private BlistaRestClient blistaRestClient;

    private boolean cachedBookAvailable(final Book book) {
        if (!availableAsDownloadMap.containsKey(book.getAghNummer())) {
            try {
                final boolean bookAvailable = blistaRestClient.bookAvailable(book.getAghNummer());
                availableAsDownloadMap.put(book.getAghNummer(), bookAvailable);
            } catch (Exception e) {
                // ignored, service not available
            }
        }
        if (availableAsDownloadMap.containsKey(book.getAghNummer())) {
            return availableAsDownloadMap.get(book.getAghNummer());
        } else {
            return false;
        }
    }

    public boolean canBeOrderedAsDownload(final Book book) {
        final boolean inBasket = basket.isInBasket(book);
        final boolean bookAvailable = cachedBookAvailable(book);
        final boolean b = !inBasket && bookAvailable;
//        LOGGER.info(book.getAghNummer() + "/" + book.getTitel() + " --> " + b);
        return b;
    }

    public boolean canBeRemovedFromBasket(final Book book) {
        final boolean inBasket = basket.isInBasket(book);
        final boolean bookAvailable = cachedBookAvailable(book);
        return inBasket && bookAvailable;
    }

}
