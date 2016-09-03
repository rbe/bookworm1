/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.catalog.web.messaging.EmailService;
import eu.artofcoding.bookworm.catalog.web.persistence.BlistaConfigurationDAO;
import eu.artofcoding.bookworm.catalog.web.persistence.BlistaOrderDAO;
import eu.artofcoding.bookworm.catalog.web.persistence.BookDAO;
import eu.artofcoding.bookworm.catalog.web.persistence.OrderDetails;
import eu.artofcoding.bookworm.catalog.web.session.HoererSession;
import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaConfiguration;
import eu.artofcoding.bookworm.common.persistence.basket.BlistaOrder;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.common.persistence.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.common.persistence.qualifier.HoererValue;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BlistaRestClient;
import eu.artofcoding.bookworm.dls.bestellung.restclient.v03.BookOrder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OrderBean.class.getName());

    @Inject
    private HoererSession hoererSession;

    @Inject
    private BlistaConfigurationDAO blistaConfigurationDAO;

    @Inject
    @HoererValue
    private transient Instance<Hoererstamm> hoererstamm;

    @Inject
    @HoererValue
    private transient Instance<HoererKennzeichen> hoererKennzeichen;

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

    @Inject
    private WishlistBean wishlistBean;

    private BlistaConfiguration blistaConfiguration;

    private Basket orderedPostalBasket;

    private Basket orderedDigitalBasket;

    private OrderDetails orderDetails = new OrderDetails();

    private String orderSubmitMessage;

    private Boolean dailyLimitReached;

    private Boolean monthlyLimitReached;

    @PostConstruct
    private void postConstruct() {
        blistaConfiguration = blistaConfigurationDAO.findById(1L);
        if (hoererSession.hasHoerernummer()) {
            Hoererstamm hoererstamm = this.hoererstamm.get();
            if (null != hoererstamm) {
                if (notEmpty(hoererstamm.getVorname()) && notEmpty(hoererstamm.getNachname())) {
                    orderDetails.setName(String.format("%s %s", hoererstamm.getVorname(), hoererstamm.getNachname()));
                }
                if (notEmpty(hoererstamm.getHoerernummer())) {
                    orderDetails.setHoerernummer(hoererstamm.getHoerernummer());
                }
            }
            HoererKennzeichen hoererKennzeichen = this.hoererKennzeichen.get();
            if (null != hoererKennzeichen && notEmpty(hoererKennzeichen.getEmail())) {
                orderDetails.setEmail(hoererKennzeichen.getEmail());
            }
        }
    }

    private boolean notEmpty(final String str) {
        return null != str && !str.trim().isEmpty();
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    private void postalDelivery() {
        emailService.sendMail(orderDetails, (PostalBasketBean) postalBasketBean);
        orderedPostalBasket = postalBasketBean.getBasket();
        postalBasketBean.wasOrdered();
    }

    private void digitalDelivery() {
        final BlistaOrder blistaOrder = new BlistaOrder();
        blistaOrder.setHoerernummer(orderDetails.getHoerernummer());
        blistaOrder.setUserId(orderDetails.getName());
        blistaOrder.setEmail(orderDetails.getEmail());
        int counter = 0;
        for (Book book : digitalBasketBean.getBasket().getBooks()) {
            if (counter < blistaConfiguration.getMaxDownloadsPerDay()) {
                blistaOrder.getBooks().add(book);
                try {
                    final BookOrder bookOrder = blistaRestClient.placeBillet(blistaOrder.getUserId(), book.getAghNummer());
                    blistaOrder.abrufkennwort(book.getAghNummer(), bookOrder.getAbrufkennwort());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "", e);
                    blistaOrder.abrufkennwort(book.getAghNummer(), "");
                }
                counter++;
            } else {
                wishlistBean.add(book);
                digitalBasketBean.remove(book);
            }
        }
        blistaOrderDAO.create(blistaOrder);
        emailService.sendMail(orderDetails, (DigitalBasketBean) digitalBasketBean);
        orderedDigitalBasket = digitalBasketBean.getBasket();
        digitalBasketBean.wasOrdered();
        monthlyLimitReached = null;
        dailyLimitReached = null;
    }

    public String placeOrder() {
        orderSubmitMessage = "Ihre Bestellung wird bearbeitet!";
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
        orderSubmitMessage = null;
        return "basket-thankyou";
    }

    public int getItemCount() {
        return postalBasketBean.getItemCount() + digitalBasketBean.getItemCount();
    }

    public boolean isMaxDownloadPerMonthReached() {
        if (null == monthlyLimitReached && hoererSession.hasHoerernummer()) {
            final LocalDate localDate = LocalDate.now();
            final List<Object> list = Arrays.asList(hoererSession.getHoerernummer(), localDate.getMonthValue(), localDate.getYear());
            final int i = blistaOrderDAO.countNamedNativeQuery("BlistaOrder.countBooksPerMonthByHoerernummer", list);
            monthlyLimitReached = i >= blistaConfiguration.getMaxDownloadsPerMonth();
        } else {
            monthlyLimitReached = false;
        }
        return monthlyLimitReached;
    }

    public boolean isMaxDownloadPerDayReached() {
        if (null == dailyLimitReached && hoererSession.hasHoerernummer()) {
            final Map<String, Object> map = new HashMap<String, Object>() {{
                put("hoerernummer", hoererSession.getHoerernummer());
            }};
            final int i = blistaOrderDAO.countNamedQuery("BlistaOrder.countBooksPerDayByHoerernummer", map);
            dailyLimitReached = i >= blistaConfiguration.getMaxDownloadsPerDay();
        } else {
            dailyLimitReached = false;
        }
        return dailyLimitReached;
    }

    public boolean canBeDownloadedAction(final Book book) {
        final DigitalBasketBean d = (DigitalBasketBean) digitalBasketBean;
        return hoererSession.hasHoerernummer() && !isMaxDownloadPerDayReached() && !isMaxDownloadPerMonthReached() && d.canBeOrderedAsDownload(book);
    }

    public Basket getOrderedPostalBasket() {
        return orderedPostalBasket;
    }

    public Basket getOrderedDigitalBasket() {
        return orderedDigitalBasket;
    }

    public boolean displayBasketLink() {
        final String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return (null != viewId && !viewId.startsWith("/basket")) && (!postalBasketBean.isEmpty() || !digitalBasketBean.isEmpty());
    }

    public boolean displayEmptyBasketLink() {
        final String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return (null != viewId && !viewId.startsWith("/basket")) && (postalBasketBean.isEmpty() && digitalBasketBean.isEmpty());
    }

    public String getOrderSubmitMessage() {
        return orderSubmitMessage;
    }

}
