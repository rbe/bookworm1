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
import eu.artofcoding.beetlejuice.persistence.PaginateableSearch;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ManagedBean
@SessionScoped
public class BookwormBean implements Serializable {

    //<editor-fold desc="Member">

    @EJB
    private BookDAO bookDAO;

    private String stichwort;

    private String autor;

    private String titel;

    private String datum;

    private PaginateableSearch<BookEntity> paginateableSearch;

    private BasketEntity basket = new BasketEntity();

    //</editor-fold>

    //<editor-fold desc="Getter and Setter">
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

    //</editor-fold>

    //<editor-fold desc="Navigation">

    private String determineNextPage() {
        String nextPage = "improve-search-term.xhtml";
        if (paginateableSearch.getTotalRowCount() > 100) {
            nextPage = "too-many-results.xhtml";
        } else if (paginateableSearch.getTotalRowCount() > 0) {
            nextPage = "result.xhtml";
        }
        return nextPage;
    }

    public String gotoSearchPage() {
        // Reset state
        stichwort = "";
        autor = "";
        titel = "";
        datum = "";
        // Go to search page
        return "search.xhtml";
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

    //</editor-fold>

}
