/*
 * bookworm
 * bookworm-web
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu/
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 04.09.12 12:14
 */

package eu.artofcoding.bookworm;

import eu.artofcoding.beetlejuice.api.BeetlejuiceConstant;
import eu.artofcoding.beetlejuice.api.persistence.QueryParameter;
import eu.artofcoding.beetlejuice.email.Postman;
import eu.artofcoding.beetlejuice.persistence.PaginateableSearch;
import eu.artofcoding.beetlejuice.template.TemplateProcessor;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
import java.util.*;

public class BookwormBean implements Serializable {

    //<editor-fold desc="Member">

    @EJB
    private BookDAO bookDAO;

    /**
     * Maximum count of search results, > will redirect to page too-many-results.xhtml.
     */
    @ManagedProperty(value = "500")
    private Integer maxSearchResults;

    private String stichwort;

    private String autor;

    private String titel;

    private String datum;

    private PaginateableSearch<BookEntity> paginateableSearch;

    private BasketEntity basket = new BasketEntity();

    @Inject
    private TemplateProcessor templateProcessor;

    @Resource(mappedName = "java:/bookworm-smtp")
    private Session session;

    @ManagedProperty(value = "WBH Online Shop")
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
            if (f.trim().length() > 0) {
                searchTerm.append(f);
            }
            if (i < terms.length) {
                searchTerm.append(" ");
            }
        }
        paginateableSearch.setSearchTerm(searchTerm.toString());
    }

    private List<QueryParameter> buildQueryParameter(Object input, String[] fields, String connector) {
        List<QueryParameter> queryParameters = new ArrayList<QueryParameter>();
        if (input instanceof String) {
            // Split search term by space
            String str = (String) input;
            String[] terms = str.toLowerCase().split(BeetlejuiceConstant.SPACE);
            // Cleanup: each term must have a length of at least 3
            terms = cleanupSearchTerms(terms, 3);
            if (terms.length > 0) {
                // Build list of QueryParameters
                for (String f : fields) {
                    QueryParameter q = new QueryParameter(f, terms, BeetlejuiceConstant.LIKE, connector);
                    queryParameters.add(q);
                }
            }
        } else if (input instanceof java.util.Date) {
            java.util.Date[] date = {(java.util.Date) input};
            // Build list of QueryParameters
            for (String f : fields) {
                QueryParameter q = new QueryParameter(f, date, BeetlejuiceConstant.GREATER_EQUAL, connector);
                queryParameters.add(q);
            }
        }
        return queryParameters;
    }

    /**
     * BOOKWORM-1 Variante 1.
     * @return String Navigation case.
     */
    public String search1() {
        paginateableSearch = new PaginateableSearch<BookEntity>(bookDAO);
        List<QueryParameter> queryParameters;
        if (null != stichwort && stichwort.length() > 0) {
            String[] fields = {"autor", "titel", "untertitel", "erlaeuterung", "suchwoerter", "titelnummer"};
            queryParameters = buildQueryParameter(stichwort, fields, BeetlejuiceConstant.OR);
            if (null != queryParameters && queryParameters.size() > 0) {
                // Execute search
                setSearchTerm(stichwort);
                paginateableSearch.executeSearch(queryParameters, BeetlejuiceConstant.OR);
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
        paginateableSearch = new PaginateableSearch<BookEntity>(bookDAO);
        List<QueryParameter> queryParameters = new ArrayList<QueryParameter>();
        if (null != autor && autor.length() > 0) {
            queryParameters.addAll(buildQueryParameter(autor, new String[]{"autor"}, BeetlejuiceConstant.OR));
        }
        if (null != titel && titel.length() > 0) {
            queryParameters.addAll(buildQueryParameter(titel, new String[]{"titel"}, BeetlejuiceConstant.OR));
        }
        if (null != datum && datum.length() > 0) {
            SimpleDateFormat sdfGer = new SimpleDateFormat("dd.MM.yyyy");
            try {
                Date _datum = sdfGer.parse(datum);
                queryParameters.addAll(buildQueryParameter(_datum, new String[]{"einstelldatum"}, BeetlejuiceConstant.OR));
            } catch (ParseException e) {
                // ignore
            }
        }
        if (queryParameters.size() > 0) {
            // Execute search
            setSearchTerm(autor, titel, datum);
            paginateableSearch.executeSearch(queryParameters, BeetlejuiceConstant.AND);
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

    public BasketEntity getBasket() {
        return basket;
    }

    public boolean isEntityInBasket(Long id) {
        if (null != id) {
            boolean inBasket = basket.isInBasket(id);
            return inBasket;
        } else {
            return false;
        }
    }

    public boolean isSelectedEntityInBasket() {
        if (null != paginateableSearch && null != paginateableSearch.getSelectedEntity()) {
            boolean inBasket = basket.isInBasket(paginateableSearch.getSelectedEntity().getId());
            return inBasket;
        } else {
            return false;
        }
    }

    public String addToBasket(Long id) {
        if (null != id) {
            BookEntity book = bookDAO.findById(id);
            basket.getBooks().add(book);
        }
        return null;
    }

    public String removeFromBasket(Long id) {
        if (null != id) {
            for (Iterator<BookEntity> iterator = basket.getBooks().iterator(); iterator.hasNext(); ) {
                BookEntity book = iterator.next();
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
        basket = new BasketEntity();
        return "basket-thankyou";
    }

    private void sendMail() throws IOException, TemplateException, MessagingException {
        // Data model for template
        final SimpleHash root = new SimpleHash();
        root.put("basket", basket);
        root.put("bestelldatum", new Date());
        // Render template depending on request locale
        String body = templateProcessor.renderTemplateToString("order.html", Locale.GERMAN, root);
        // Set recipient
        Set<String> recipients = new HashSet<String>();
        recipients.add(basket.getEmail());
        recipients.add(mailUser);
        // Send email
        Postman postman = new Postman(session);
        postman.sendHtmlMail(String.format("%s <%s>", mailName, mailUser), recipients, mailSubject, body);
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
