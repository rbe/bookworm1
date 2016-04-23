/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.beetlejuice.api.persistence.QueryConfiguration;
import eu.artofcoding.beetlejuice.api.persistence.QueryParameter;
import eu.artofcoding.beetlejuice.api.persistence.QueryVariant;
import eu.artofcoding.beetlejuice.email.Postman;
import eu.artofcoding.beetlejuice.email.cdi.QPostman;
import eu.artofcoding.beetlejuice.persistence.PaginateableSearch;
import eu.artofcoding.beetlejuice.template.TemplateProcessor;
import eu.artofcoding.bookworm.catalog.web.persistence.BookDAO;
import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.book.Sachgebiet;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.AND;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.GREATER_EQUAL;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.LIKE;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.OR;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.SPACE;
import static eu.artofcoding.beetlejuice.email.cdi.TransportType.SSL_TLS;

@SessionScoped
public class BookwormBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(BookwormBean.class.getName());

    //<editor-fold desc="Member">

    @Inject
    private BookDAO bookDAO;

    /**
     * Maximum count of search results, > will redirect to page too-many-results.xhtml.
     */
    @ManagedProperty(value = "500")
    private Integer maxSearchResults;

    private String stichwort;

    private String sachgebiet;

    private String autor;

    private String titel;

    private String datum;

    private PaginateableSearch<Book> paginateableSearch;

    private Basket basket = new Basket();

    private Basket orderedBasket;

    @Inject
    private TemplateProcessor templateProcessor;

    @Inject
    @QPostman(transportType = SSL_TLS)
    private Postman postman;

    @Resource(name = "bookwormMail")
    private Session session;

    @ManagedProperty(value = "WBH Katalog")
    private String mailName;

    @ManagedProperty(value = "wbh@wbh-online.de")
    private String mailUser;

    @ManagedProperty(value = "Ihre Bestellung bei der WBH")
    private String mailSubject;

    //</editor-fold>

    @PostConstruct
    public void initialize() {
        // Initialize TemplateProcessor
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        templateProcessor.addTemplateLoader(context, "/resources/bookworm");
        templateProcessor.getConfiguration().setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    //<editor-fold desc="Getter and Setter">

    public Integer getMaxSearchResults() {
        return maxSearchResults;
    }

    public void setMaxSearchResults(Integer maxSearchResults) {
        this.maxSearchResults = maxSearchResults;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getStichwort() {
        return stichwort;
    }

    public void setStichwort(String stichwort) {
        this.stichwort = stichwort;
    }

    public String getSachgebiet() {
        return sachgebiet;
    }

    public void setSachgebiet(String sachgebiet) {
        this.sachgebiet = sachgebiet;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    //</editor-fold>

    //<editor-fold desc="Searching">

    public Sachgebiet[] getSachgebiete() {
        return Book.SACHGEBIET_ENTITY.toArray(new Sachgebiet[Book.SACHGEBIET_ENTITY.size()]);
    }

    /**
     * Cleanup search terms. Each term must have a length of at least 'minLength' after trimming.
     * @param terms     String array with terms.
     * @param minLength Minimum length of search term.
     * @return String array with terms having a length >= minLength.
     */
    private String[] cleanupSearchTerms(String[] terms, int minLength) {
        List<String> cleanedTerms = new ArrayList<>(terms.length);
        for (String t : terms) {
            if (null != t && t.trim().length() >= minLength) {
                cleanedTerms.add(t);
            }
        }
        terms = cleanedTerms.toArray(new String[cleanedTerms.size()]);
        return terms;
    }

    private void setSearchTerm(String... terms) {
        StringBuilder searchTerm = new StringBuilder();
        for (int i = 0, fieldsLength = terms.length; i < fieldsLength; i++) {
            String f = terms[i];
            if (null != f && f.length() > 0 && f.trim().length() > 0) {
                searchTerm.append(f);
            }
            if (i < terms.length) {
                searchTerm.append(" ");
            }
        }
        paginateableSearch.setSearchTerm(searchTerm.toString());
    }

    private List<QueryParameter> buildQueryParameter(Object input, String[] fields, String connector, boolean checkLength, boolean isNotNull) {
        List<QueryParameter> queryParameters = new ArrayList<>();
        if (input instanceof String) {
            // Split search term by space
            String str = (String) input;
            String[] terms = str.toLowerCase().split(SPACE);
            if (checkLength) {
                // Cleanup: each term must have a length of at least 3
                terms = cleanupSearchTerms(terms, 3);
            }
            if (terms.length > 0) {
                // Build list of QueryParameters
                for (String f : fields) {
                    QueryParameter q = new QueryParameter(f, terms, LIKE, connector);
                    queryParameters.add(q);
                    if (isNotNull) {
                        q.setAddIsNotNull(true);
                    }
                }
            }
        } else if (input instanceof java.util.Date) {
            java.util.Date[] date = {(java.util.Date) input};
            // Build list of QueryParameters
            for (String f : fields) {
                QueryParameter q = new QueryParameter(f, date, GREATER_EQUAL, connector);
                queryParameters.add(q);
            }
        }
        return queryParameters;
    }

    /**
     * BOOKWORM-1 Variante 1 oder 2?
     * @return String Navigation case.
     */
    public String search() {
        String nextPage = "ErrorCode500";
        try {
            if (null != stichwort && stichwort.length() > 0) {
                nextPage = search1();
            } else {
                nextPage = search2();
            }
        } catch (Exception e) {
            paginateableSearch = null;
            LOGGER.log(Level.SEVERE, "", e);
        }
        return nextPage;
    }

    /**
     * BOOKWORM-1 Variante 1.
     * @return String Navigation case.
     */
    public String search1() {
        List<QueryParameter> queryParameters;
        if (null != stichwort && stichwort.length() > 0) {
            String[] fields1 = {"sachgebiet", "autor", "titel", "untertitel", "erlaeuterung", "suchwoerter", "titelnummer"};
            queryParameters = buildQueryParameter(stichwort, fields1, OR, true, false);
            if (queryParameters.size() > 0) {
                //bookDAO.setEntityManager(entityManager);
                paginateableSearch = new PaginateableSearch<>(bookDAO);
                // Execute search
                setSearchTerm(stichwort);
                QueryConfiguration queryConfiguration = new QueryConfiguration();
                queryConfiguration.setQueryVariant(QueryVariant.VARIANT_2);
                queryConfiguration.setQueryParameters(queryParameters);
                queryConfiguration.setTableName("Book");
                queryConfiguration.setNativeQuery(true);
                paginateableSearch.executeSearch(queryConfiguration, AND, new String[]{"o.autor", "o.titel"});
            }
        }
        // Go to next page
        return determineNextPage();
    }

    /**
     * BOOKWORM-1 Variante 2.
     * @return String Navigation case.
     */
    public String search2() {
        List<QueryParameter> queryParameters = new ArrayList<>();
        if (null != sachgebiet && sachgebiet.length() > 0) {
            queryParameters.addAll(buildQueryParameter(sachgebiet, new String[]{"sachgebiet"}, OR, false, false));
        }
        if (null != autor && autor.length() > 0) {
            queryParameters.addAll(buildQueryParameter(autor, new String[]{"autor"}, AND, true, false));
        }
        if (null != titel && titel.length() > 0) {
            queryParameters.addAll(buildQueryParameter(titel, new String[]{"titel"}, AND, true, false));
        }
        if (null != datum && datum.length() > 0) {
            SimpleDateFormat sdfGer = new SimpleDateFormat("dd.MM.yyyy");
            try {
                Date _datum = sdfGer.parse(datum);
                queryParameters.addAll(buildQueryParameter(_datum, new String[]{"einstelldatum"}, OR, true, false));
            } catch (ParseException e) {
                // ignore
            }
        }
        if (queryParameters.size() > 0) {
            paginateableSearch = new PaginateableSearch<>(bookDAO);
            // Execute search
            setSearchTerm(sachgebiet, autor, titel, datum);
            QueryConfiguration queryConfiguration = new QueryConfiguration();
            queryConfiguration.setQueryVariant(QueryVariant.VARIANT_1);
            queryConfiguration.setQueryParameters(queryParameters);
            paginateableSearch.executeSearch(queryConfiguration, AND, new String[]{"o.autor", "o.titel"});
        }
        // Go to next page
        return determineNextPage();
    }

    public PaginateableSearch getPaginateableSearch() {
        return paginateableSearch;
    }

    public String showDetail(Long id) {
        if (null != paginateableSearch.selectEntityById(id)) {
            return "bookdetail.xhtml";
        }
        return null;
    }

    //</editor-fold>

    //<editor-fold desc="Basket">

    public Basket getBasket() {
        return basket;
    }

    public Basket getOrderedBasket() {
        return orderedBasket;
    }

    public boolean isEntityInBasket(Long id) {
        if (null != id) {
            return basket.isInBasket(id);
        } else {
            return false;
        }
    }

    public boolean isSelectedEntityInBasket() {
        if (null != paginateableSearch && null != paginateableSearch.getSelectedEntity()) {
            final Long id = paginateableSearch.getSelectedEntity().getId();
            return basket.isInBasket(id);
        } else {
            return false;
        }
    }

    public String addToBasket(Long id) {
        if (null != id) {
            Book book = bookDAO.findById(id);
            basket.getBooks().add(book);
        }
        return null;
    }

    public String removeFromBasket(Long id) {
        if (null != id) {
            for (Iterator<Book> iterator = basket.getBooks().iterator(); iterator.hasNext(); ) {
                Book book = iterator.next();
                if (book.getId().equals(id)) {
                    iterator.remove();
                }
            }
        }
        return null;
    }

    public String addSelectedEntityToBasket() {
        if (null != paginateableSearch && null != paginateableSearch.getSelectedEntity()) {
            addToBasket(paginateableSearch.getSelectedEntity().getId());
        }
        return null;
    }

    public String removeSelectedEntityFromBasket() {
        if (null != paginateableSearch && null != paginateableSearch.getSelectedEntity()) {
            removeFromBasket(paginateableSearch.getSelectedEntity().getId());
        }
        return null;
    }

    public String placeOrder() throws IOException, TemplateException, MessagingException {
        sendMail();
        orderedBasket = basket;
        basket = new Basket();
        return "basket-thankyou";
    }

    private void sendMail() throws IOException, TemplateException, MessagingException {
        // Data model for template
        final SimpleHash root = new SimpleHash();
        root.put("basket", basket);
        root.put("bestelldatum", new Date());
        // Render template depending on request locale
        String body = templateProcessor.renderTemplateToString("catalog/order.html", Locale.GERMAN, root);
        // Set recipient
        Set<String> recipients = new HashSet<>();
        recipients.add(basket.getEmail());
        recipients.add(mailUser);
        // Send email
        postman.setSession(session);
        final String fromAddress = String.format("%s <%s>", mailName, mailUser);
        postman.sendHtmlMail(fromAddress, recipients, mailSubject, body);
    }

    //</editor-fold>

    //<editor-fold desc="Navigation">

    private String determineNextPage() {
        String nextPage = "improve-search-term.xhtml";
        if (maxSearchResults > 0 && paginateableSearch.getTotalRowCount() > maxSearchResults) {
            nextPage = "too-many-results.xhtml";
        } else if (paginateableSearch.getTotalRowCount() > 0) {
            nextPage = "result.xhtml";
        }
        return nextPage;
    }

    public String resetStateAndGotoSearchPage() {
        // Reset state
        sachgebiet = "";
        stichwort = "";
        autor = "";
        titel = "";
        datum = "";
        //
        paginateableSearch = null;
        // Go to search page
        return "search";
    }

    public boolean isNextButtonEnabled() {
        return null != paginateableSearch && paginateableSearch.hasNextPage();
    }

    public String nextPage() {
        paginateableSearch.gotoNextPage();
        return null;
    }

    public boolean isPreviousButtonEnabled() {
        return null != paginateableSearch && paginateableSearch.hasPreviousPage();
    }

    public String previousPage() {
        paginateableSearch.gotoPreviousPage();
        return null;
    }

    public String nextBook() {
        paginateableSearch.next();
        return null;
    }

    public String previousBook() {
        paginateableSearch.previous();
        return null;
    }

    public boolean displayBackToResultListLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        // !facesContext.viewRoot.viewId.equalsIgnoreCase('/result.xhtml') and bookwormBean.paginateableSearch != null
        return !viewId.equals("/result.xhtml") && null != paginateableSearch;
    }

    public boolean displaySearchLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        // !facesContext.viewRoot.viewId.equalsIgnoreCase('/search.xhtml') and bookwormBean.paginateableSearch == null
        return !viewId.equals("/search.xhtml") && null == paginateableSearch;
    }

    public boolean displaySearchAgainLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        // !facesContext.viewRoot.viewId.equalsIgnoreCase('/search.xhtml') and bookwormBean.paginateableSearch != null
        return !viewId.equals("/search.xhtml") && null != paginateableSearch;
    }

    public boolean displayBasketLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        // !facesContext.viewRoot.viewId.equalsIgnoreCase('/basket.xhtml') and bookwormBean.basket.itemCount() > 0
        return !viewId.startsWith("/basket") && basket.itemCount() > 0;
    }

    public boolean displayEmptyBasketLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        // !facesContext.viewRoot.viewId.equalsIgnoreCase('/basket.xhtml') and bookwormBean.basket.itemCount() == 0
        return !viewId.startsWith("/basket") && basket.itemCount() == 0;
    }

    //</editor-fold>

}
