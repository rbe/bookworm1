/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.catalog.web.session.HoererSession;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@DigitalOrder
@SessionScoped
public class DigitalBasketBean extends AbstractBasketBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalBasketBean.class);

    private final Map<String, Boolean> availableAsDownloadMap = new HashMap<>();

    @Inject
    private HoererSession hoererSession;

    @Inject
    private BlistaRestClient blistaRestClient;

    private boolean cachedBookAvailable(final Book book) {
        if (!availableAsDownloadMap.containsKey(book.getAghNummer())) {
            try {
                final boolean bookAvailable = blistaRestClient.bookAvailable(book.getAghNummer());
                availableAsDownloadMap.put(book.getAghNummer(), bookAvailable);
            } catch (Exception e) {
                // ignored, service not available at this time
                LOGGER.warn("Cannot talk to bookworm.dls");
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
        return hoererSession.hasHoerernummer() && !inBasket && bookAvailable;
    }

    public boolean canBeRemovedFromBasket(final Book book) {
        final boolean inBasket = basket.isInBasket(book);
        final boolean bookAvailable = cachedBookAvailable(book);
        return hoererSession.hasHoerernummer() && inBasket && bookAvailable;
    }

}
