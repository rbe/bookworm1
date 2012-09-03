/*
 * bookworm
 * bookworm-web
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu/
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 29.08.12 11:14
 */

package eu.artofcoding.bookworm;

import eu.artofcoding.beetlejuice.jpa.PaginateableSearch;
import eu.artofcoding.wbh.bookworm.BookEntity;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ManagedBean
@SessionScoped
public class BookwormSearchBean implements Serializable {

    //<editor-fold desc="Fields">
    @EJB
    private BookDAO bookDAO;
    private String stichwort;
    private String autor;
    private String titel;
    private String datum;
    private PaginateableSearch<BookEntity> result;
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
    public String search() {
        // Create result object
        result = new PaginateableSearch<BookEntity>(bookDAO);
        // Query parameters
        String namedQuery = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (null != stichwort && stichwort.length() > 0) {
            result.setSearchTerm(stichwort);
            parameters.put("stichwort", "%" + stichwort + "%");
            namedQuery = "findByStichwort";
        } else if (null != autor && autor.length() > 0) {
            result.setSearchTerm(autor);
            parameters.put("autor", "%" + autor + "%");
            namedQuery = "findByAutor";
        } else if (null != titel && titel.length() > 0) {
            result.setSearchTerm(titel);
            parameters.put("titel", "%" + titel + "%");
            namedQuery = "findByTitel";
        } else if (null != datum && datum.length() > 0) {
            result.setSearchTerm(datum);
            parameters.put("datum", "%" + datum + "%");
            namedQuery = "findByDatum";
        }
        if (null != namedQuery) {
            result.executeSearch(namedQuery, parameters);
        }
        // Go to result page
        return "result.xhtml";
    }

    public PaginateableSearch getResult() {
        return result;
    }

    public String showDetail(Long id) {
        if (null != result.selectEntityById(id)) {
            return "bookdetail.xhtml";
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Navigation">
    public String gotoSearchPage() {
        // Go to search page
        return "search.xhtml";
    }

    public boolean isNextButtonEnabled() {
        return result.hasNextPage();
    }

    public String nextPage() {
        result.gotoNextPage();
        return null;
    }

    public boolean isPreviousButtonEnabled() {
        return result.hasPreviousPage();
    }

    public String previousPage() {
        result.gotoPreviousPage();
        return null;
    }

    public String nextBook() {
        result.next();
        return null;
    }

    public String previousBook() {
        result.previous();
        return null;
    }
    //</editor-fold>

}
