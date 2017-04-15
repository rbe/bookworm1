/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 */

package eu.artofcoding.bookworm.catalog.web.messaging;

import eu.artofcoding.beetlejuice.email.Postman;
import eu.artofcoding.beetlejuice.email.cdi.QPostman;
import eu.artofcoding.beetlejuice.template.TemplateProcessor;
import eu.artofcoding.bookworm.catalog.web.persistence.OrderDetails;
import eu.artofcoding.bookworm.catalog.web.view.DigitalBasketBean;
import eu.artofcoding.bookworm.catalog.web.view.PostalBasketBean;
import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static eu.artofcoding.beetlejuice.email.cdi.TransportType.SSL_TLS;

@Named
public class EmailService implements Serializable {

    private final transient Postman postman;

    private final transient TemplateProcessor templateProcessor;

    @Resource(name = "bookwormMail")
    private transient Session session;

    @Inject
    public EmailService(final @QPostman(transportType = SSL_TLS) Postman postman, final TemplateProcessor templateProcessor) {
        this.postman = postman;
        this.templateProcessor = templateProcessor;
    }

    @PostConstruct
    private void initialize() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        templateProcessor.addTemplateLoader(context, "/resources/bookworm");
        templateProcessor.getConfiguration().setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    private void sendMail(final OrderDetails orderDetails, final Basket basket, final String subject, final String template) {
        // Data model for template
        final SimpleHash model = new SimpleHash();
        model.put("orderdetails", orderDetails);
        model.put("basket", basket);
        model.put("bestelldatum", new Date());
        try {
            // Render template depending on request locale
            final String body = templateProcessor.renderTemplateToString(template, Locale.GERMAN, model);
            final Set<String> recipients = new HashSet<>();
            recipients.add(orderDetails.getEmail());
            try {
                final byte[] mailFromProperty = session.getProperty("mail.from").getBytes("ISO-8859-1");
                final String mailFrom = new String(mailFromProperty, "UTF-8");
                recipients.add(mailFrom);
                // Send email
                session.getProperties().setProperty("mail.mime.charset", "UTF8");
                postman.setSession(session);
                postman.sendHtmlMail(mailFrom, recipients, subject, body);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } catch (TemplateException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMail(final OrderDetails orderDetails, final DigitalBasketBean basketBean) {
        sendMail(orderDetails, basketBean.getBasket(),
                "Ihre Download-Bestellung bei der WBH", "catalog/digitalOrderReceipt.ftl");
    }

    public void sendMail(final OrderDetails orderDetails, final PostalBasketBean basketBean) {
        sendMail(orderDetails, basketBean.getBasket(),
                "Ihre CD-Bestellung bei der WBH", "catalog/postalOrderReceipt.ftl");
    }

}
