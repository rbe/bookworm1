/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.catalog.web.messaging.EmailService;
import eu.artofcoding.bookworm.catalog.web.persistence.BlistaOrderDAO;
import eu.artofcoding.bookworm.catalog.web.persistence.BookDAO;
import eu.artofcoding.bookworm.catalog.web.persistence.HoerernummerFilter;
import eu.artofcoding.bookworm.catalog.web.persistence.OrderDetails;
import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.common.persistence.qualifier.HoererValue;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BookOrder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OrderBean.class.getName());

    @Inject
    @HoererValue
    private Hoererstamm hoererstamm;

    @Inject
    @HoererValue
    private HoererKennzeichen hoererKennzeichen;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private BlistaOrderDAO blistaOrderDAO;

    @Inject
    @PostalOrder
    private BasketBean postalBasketBean;

    @Inject
    @DigitalOrder
    private BasketBean digitalBasketBean;

    @Inject
    private EmailService emailService;

    @Inject
    private BlistaRestClient blistaRestClient;

    private Basket orderedPostalBasket;

    private Basket orderedDigitalBasket;

    private OrderDetails orderDetails = new OrderDetails();

    @PostConstruct
    private void postConstruct() {
        if (HoerernummerFilter.hasHoerernummer()) {
            orderDetails.setName(String.format("%s %s", hoererstamm.getVorname(), hoererstamm.getNachname()));
            orderDetails.setHoerernummer(hoererstamm.getHoerernummer());
            orderDetails.setEmail(hoererKennzeichen.getEmail());
        }
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    private void postalDelivery() {
        emailService.sendMail(orderDetails, postalBasketBean.getBasket(), "catalog/postalOrderReceipt.html");
        orderedPostalBasket = postalBasketBean.getBasket();
        postalBasketBean.wasOrdered();
    }

    private void digitalDelivery() {
        final BlistaOrder blistaOrder = new BlistaOrder();
        blistaOrder.setHoerernummer(orderDetails.getHoerernummer());
        blistaOrder.setUserId(orderDetails.getName());
        blistaOrder.setEmail(orderDetails.getEmail());
        for (Book book : digitalBasketBean.getBasket().getBooks()) {
            blistaOrder.getBooks().add(book);
            try {
                final BookOrder bookOrder = blistaRestClient.placeBillet(blistaOrder.getUserId(), book.getAghNummer());
                blistaOrder.abrufkennwort(book.getAghNummer(), bookOrder.getAbrufkennwort());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "", e);
                blistaOrder.abrufkennwort(book.getAghNummer(), "");
            }
        }
        try {
            blistaOrderDAO.create(blistaOrder);
            emailService.sendMail(orderDetails, digitalBasketBean.getBasket(), "catalog/digitalOrderReceipt.html");
            orderedDigitalBasket = digitalBasketBean.getBasket();
            digitalBasketBean.wasOrdered();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }

    public int getItemCount() {
        return postalBasketBean.getItemCount() + digitalBasketBean.getItemCount();
    }

    public Basket getOrderedPostalBasket() {
        return orderedPostalBasket;
    }

    public Basket getOrderedDigitalBasket() {
        return orderedDigitalBasket;
    }

    public String placeOrder() {
        orderedPostalBasket = null;
        final boolean wantPostalDelivery = !postalBasketBean.isEmpty();
        if (wantPostalDelivery) {
            try {
                postalDelivery();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "", e);
                return "basket-error";
            }
        }
        orderedDigitalBasket = null;
        final boolean wantDigitalDelivery = !digitalBasketBean.isEmpty();
        if (wantDigitalDelivery) {
            try {
                digitalDelivery();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "", e);
                return "basket-error";
            }
        }
        return "basket-thankyou";
    }

    public boolean displayBasketLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return !viewId.startsWith("/basket") && (!postalBasketBean.isEmpty() || !digitalBasketBean.isEmpty());
    }

    public boolean displayEmptyBasketLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return !viewId.startsWith("/basket") && (postalBasketBean.isEmpty() && digitalBasketBean.isEmpty());
    }

}
