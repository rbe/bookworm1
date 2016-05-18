package eu.artofcoding.bookworm.catalog.web.messaging;

import eu.artofcoding.beetlejuice.email.Postman;
import eu.artofcoding.beetlejuice.email.cdi.QPostman;
import eu.artofcoding.beetlejuice.template.TemplateProcessor;
import eu.artofcoding.bookworm.catalog.web.persistence.OrderDetails;
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

    @Inject
    @QPostman(transportType = SSL_TLS)
    private transient Postman postman;

    @Inject
    private transient TemplateProcessor templateProcessor;

    @Resource(name = "bookwormMail")
    private transient Session session;

    private String mailSubject = "Ihre Bestellung bei der WBH";

    @PostConstruct
    private void initialize() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        templateProcessor.addTemplateLoader(context, "/resources/bookworm");
        templateProcessor.getConfiguration().setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    public void sendMail(final OrderDetails orderDetails, final Basket basket, final String template) {
        // Data model for template
        final SimpleHash root = new SimpleHash();
        root.put("orderdetails", orderDetails);
        root.put("basket", basket);
        root.put("bestelldatum", new Date());
        // Render template depending on request locale
        try {
            final String body = templateProcessor.renderTemplateToString(template, Locale.GERMAN, root);
            // Set recipient
            final Set<String> recipients = new HashSet<>();
            recipients.add(orderDetails.getEmail());
            try {
                final String mailFrom = new String(session.getProperty("mail.from").getBytes("ISO-8859-1"), "UTF-8");
                recipients.add(mailFrom);
                // Send email
                session.getProperties().setProperty("mail.mime.charset", "UTF8");
                postman.setSession(session);
                postman.sendHtmlMail(mailFrom, recipients, mailSubject, body);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } catch (TemplateException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
