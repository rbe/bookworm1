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
import eu.artofcoding.bookworm.catalog.web.persistence.OrderDetails;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BookOrder;

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
    private BookDAO bookDAO;

    @Inject
    private BlistaOrderDAO blistaOrderDAO;

    @Inject
    @PostalOrder
    private BasketBean postalDelivery;

    @Inject
    @DigitalOrder
    private BasketBean digitalDelivery;

    @Inject
    private EmailService emailService;

    @Inject
    private BlistaRestClient blistaRestClient;

    private OrderDetails orderDetails = new OrderDetails();

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    private void postalDelivery() {
        emailService.sendMail(orderDetails, postalDelivery.getBasket(), "catalog/postalOrderReceipt.html");
    }

    private void digitalDelivery() {
        emailService.sendMail(orderDetails, postalDelivery.getBasket(), "catalog/digtalOrderReceipt.html");
        final BlistaOrder blistaOrder = new BlistaOrder();
        blistaOrder.setHoerernummer(orderDetails.getHoerernummer());
        blistaOrder.setUserId(orderDetails.getName());
        blistaOrder.setEmail(orderDetails.getEmail());
        for (Book book : digitalDelivery.getBasket().getBooks()) {
            blistaOrder.getBooks().add(book);
            try {
                final BookOrder bookOrder = blistaRestClient.placeBillet(blistaOrder.getUserId(), book.getAghNummer());
                blistaOrder.abrufkennwort(book.getAghNummer(), bookOrder.getAbrufkennwort());
            } catch (Exception e) {
                blistaOrder.abrufkennwort(book.getAghNummer(), "");
            }
        }
        try {
            blistaOrderDAO.create(blistaOrder);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }

    public int getItemCount() {
        return postalDelivery.getItemCount() + digitalDelivery.getItemCount();
    }

    public String placeOrder() {
        final boolean wantPostalDelivery = !postalDelivery.isEmpty();
        if (wantPostalDelivery) {
            try {
                postalDelivery();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "", e);
                return "basket-error";
            }
        }
        final boolean wantDigitalDelivery = !digitalDelivery.isEmpty();
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
        return !viewId.startsWith("/basket") && (!postalDelivery.isEmpty() || !digitalDelivery.isEmpty());
    }

    public boolean displayEmptyBasketLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return !viewId.startsWith("/basket") && (postalDelivery.isEmpty() && digitalDelivery.isEmpty());
    }

}
