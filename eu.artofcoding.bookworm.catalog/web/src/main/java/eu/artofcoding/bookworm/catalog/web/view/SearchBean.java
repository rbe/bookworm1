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
import eu.artofcoding.beetlejuice.persistence.PaginateableSearch;
import eu.artofcoding.bookworm.catalog.web.persistence.BookDAO;
import eu.artofcoding.bookworm.catalog.web.session.HoererSession;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import eu.artofcoding.bookworm.common.persistence.book.Sachgebiet;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.AND;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.GREATER_EQUAL;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.LIKE;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.OR;
import static eu.artofcoding.beetlejuice.api.BeetlejuiceConstant.SPACE;

@Named
@SessionScoped
public class SearchBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(SearchBean.class.getName());

    @Inject
    private HoererSession hoererSession;

    @Inject
    private BookDAO bookDAO;

    /**
     * Maximum count of search results, > will redirect to page too-many-results.xhtml.
     */
    private Integer maxSearchResults = 0;

    private String stichwort;

    private String sachgebiet;

    private String autor;

    private String titel;

    private String datum;

    private PaginateableSearch<Book> paginateableSearch;

    public Integer getMaxSearchResults() {
        return maxSearchResults;
    }

    public void setMaxSearchResults(Integer maxSearchResults) {
        this.maxSearchResults = maxSearchResults;
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

    public Sachgebiet[] getSachgebiete() {
        return Book.SACHGEBIET_ENTITY.toArray(new Sachgebiet[Book.SACHGEBIET_ENTITY.size()]);
    }

    public boolean hasHoerernummer() {
        return hoererSession.hasHoerernummer();
    }

    /**
     * Cleanup search terms. Each term must have a length of at least 'minLength' after trimming.
     *
     * @param terms String array with terms.
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
        final List<QueryParameter> queryParameters = new ArrayList<>();
        if (input instanceof String) {
            // Split search term by space
            final String str = (String) input;
            String[] terms = str.toLowerCase().split(SPACE);
            if (checkLength) {
                // Cleanup: each term must have a length of at least 3
                terms = cleanupSearchTerms(terms, 3);
            }
            if (terms.length > 0) {
                // Build list of QueryParameters
                for (String f : fields) {
                    final QueryParameter q = new QueryParameter(f, terms, LIKE, connector);
                    queryParameters.add(q);
                    if (isNotNull) {
                        q.setAddIsNotNull(true);
                    }
                }
            }
        } else if (input instanceof Date) {
            final Date[] date = {(Date) input};
            // Build list of QueryParameters
            for (String f : fields) {
                final QueryParameter q = new QueryParameter(f, date, GREATER_EQUAL, connector);
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
    private String search1() {
        List<QueryParameter> queryParameters;
        if (null != stichwort && stichwort.length() > 0) {
            final String[] fields1 = {"sachgebiet", "autor", "titel", "untertitel", "erlaeuterung", "suchwoerter", "titelnummer"};
            queryParameters = buildQueryParameter(stichwort, fields1, OR, true, false);
            if (queryParameters.size() > 0) {
                paginateableSearch = new PaginateableSearch<>(bookDAO);
                setSearchTerm(stichwort);
                final QueryConfiguration queryConfiguration = new QueryConfiguration();
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
    private String search2() {
        final List<QueryParameter> queryParameters = new ArrayList<>();
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
            final SimpleDateFormat sdfGer = new SimpleDateFormat("dd.MM.yyyy");
            try {
                final Date _datum = sdfGer.parse(datum);
                queryParameters.addAll(buildQueryParameter(_datum, new String[]{"einstelldatum"}, OR, true, false));
            } catch (ParseException e) {
                // ignore
            }
        }
        if (queryParameters.size() > 0) {
            paginateableSearch = new PaginateableSearch<>(bookDAO);
            // Execute search
            setSearchTerm(sachgebiet, autor, titel, datum);
            final QueryConfiguration queryConfiguration = new QueryConfiguration();
            queryConfiguration.setQueryVariant(QueryVariant.VARIANT_1);
            queryConfiguration.setQueryParameters(queryParameters);
            paginateableSearch.executeSearch(queryConfiguration, AND, new String[]{"o.autor", "o.titel"});
        }
        // Go to next page
        return determineNextPage();
    }

    public PaginateableSearch<Book> getPaginateableSearch() {
        return paginateableSearch;
    }

    public Book getSelectedBook() {
        if (null != paginateableSearch) {
            return paginateableSearch.getSelectedEntity();
        } else {
            return null;
        }
    }

    public String showDetail(final Book book) {
        if (null != paginateableSearch.selectEntityById(book.getId())) {
            return "bookdetail.xhtml";
        }
        return null;
    }

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
        return !viewId.equals("/result.xhtml") && null != paginateableSearch;
    }

    public boolean displaySearchLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return !viewId.equals("/search.xhtml") && null == paginateableSearch;
    }

    public boolean displaySearchAgainLink() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return !viewId.equals("/search.xhtml") && null != paginateableSearch;
    }

}
