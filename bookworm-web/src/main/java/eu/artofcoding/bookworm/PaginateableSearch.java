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

import eu.artofcoding.beetlejuice.api.GenericEntity;
import eu.artofcoding.beetlejuice.jpa.GenericDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PaginateableSearch<T extends GenericEntity> implements Serializable {

    /**
     * DAO.
     */
    private GenericDAO<T> dao;

    /**
     * The search term.
     */
    private String searchTerm;

    /**
     * Named query.
     */
    private String namedQuery;

    /**
     * Parameters for named query.
     */
    private Map<String, Object> queryParameters;

    /**
     * Offset.
     */
    private int offset;

    /**
     * Size of page (currentPage to fetch).
     */
    private int pageSize = 25;

    /**
     * Turn-around mode?
     * Decides about how pages are turned at beginning or end, e.g.:
     * last page + 1 -> first page or last page.
     */
    private boolean turnAroundMode;

    /**
     * Number of pages depending on total row count
     */
    private int pageCount;

    /**
     * Total row count for query.
     */
    private long totalRowCount;

    /**
     * Index of current page in relation to total result set.
     */
    private int currentPageIndex;

    /**
     * List with entities on current page, size <= pageSize.
     */
    private List<T> currentPage;

    /**
     * Index of selected entity, 0 <= index < pageSize.
     */
    private int indexOnCurrentPage;

    /**
     * Index of selected entity, 0 <= index < totalRowCount.
     */
    private int selectedEntityTotalIndex;

    /**
     * Selected entity reference.
     */
    private T selectedEntity;

    /**
     * Constructor.
     * @param dao DAO to use for queries, <code>instanceof GenericDAO&lt;T></code>.
     */
    public PaginateableSearch(GenericDAO<T> dao) {
        this.dao = dao;
    }

    public boolean isTurnAroundMode() {
        return turnAroundMode;
    }

    public void setTurnAroundMode(boolean turnAroundMode) {
        this.turnAroundMode = turnAroundMode;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<T> executeSearch(String namedQuery, Map<String, Object> parameters) {
        this.namedQuery = namedQuery;
        this.queryParameters = parameters;
        // Execute query (first page)
        offset = 0;
        currentPage = dao.findAll(namedQuery, parameters, offset, pageSize);
        // Count rows, pages
        totalRowCount = dao.countAllWithCondition(parameters, "AND");
        pageCount = Math.max(1, (int) Math.ceil(1.0 * totalRowCount / pageSize));
        // Return first page
        return currentPage;
    }

    public void gotoPage(int page) {
        // Set page index
        currentPageIndex = page;
        // Calculate offset in result set
        offset = currentPageIndex * pageSize;
        // Execute query for page
        currentPage = dao.findAll(namedQuery, queryParameters, offset, pageSize);
        // Reset pointer
        indexOnCurrentPage = 0;
        selectedEntityTotalIndex = offset;
    }

    public long getTotalRowCount() {
        return totalRowCount;
    }

    //<editor-fold desc="Pagination">
    public int getOffset() {
        return offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets a new page size.
     * If we have executed the query before, go to first page.
     * @param pageSize New page size.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (totalRowCount > 0) {
            gotoPage(0);
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<T> getCurrentPage() {
        return currentPage;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getSelectedEntityTotalIndex() {
        return selectedEntityTotalIndex;
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    public T selectEntityById(Long id) {
        selectedEntity = null;
        // Find entity on current page
        T entity = null;
        for (int _indexOnCurrentPage = 0; _indexOnCurrentPage < currentPage.size(); _indexOnCurrentPage++) {
            entity = currentPage.get(_indexOnCurrentPage);
            if (entity.getId().equals(id)) {
                selectedEntity = entity;
                indexOnCurrentPage = _indexOnCurrentPage;
                selectedEntityTotalIndex = currentPageIndex * pageSize + indexOnCurrentPage;
            }
        }
        // TODO Entity not found on current page
        if (null == selectedEntity) {
        }
        return selectedEntity;
    }

    public T selectEntityByIndex(Long index) {
        selectedEntity = null;
        // Calculate page for index
        int neededPage = (int) (index / pageSize);
        gotoPage(neededPage);
        return selectedEntity;
    }

    public boolean hasNextPage() {
        return currentPageIndex < pageCount - 1;
    }

    private void pointToFirstEntityOnCurrentPage() {
        // Position pointer on first entity on page (we're coming from the bottom of the previous page)
        indexOnCurrentPage = 0;
        selectedEntity = currentPage.get(indexOnCurrentPage);
        selectedEntityTotalIndex = currentPageIndex * pageSize + indexOnCurrentPage;
    }

    private void pointToLastEntityOnCurrentPage() {
        // Position pointer on last entity on page (we're coming from the top of the next page)
        indexOnCurrentPage = pageSize - 1; // TODO 120 entites, 4 volle seiten, 1 mit 20
        selectedEntityTotalIndex = currentPageIndex * pageSize + indexOnCurrentPage;
    }

    private void pointToFirstEntityOnFirstPage() {
        // Position pointer on first entity on first page (we can't go back anymore)
        selectedEntityTotalIndex = 0;
        indexOnCurrentPage = 0;
    }

    private void pointToLastEntityOnLastPage() {
        // Position pointer on last entity on last page (turn around)
        selectedEntityTotalIndex = (int) totalRowCount;
        indexOnCurrentPage = (int) totalRowCount - 1;
    }

    public boolean pointerIsAtLastEntity() {
        boolean onLastPage = currentPageIndex == pageCount - 1;
        boolean afterLastEntity = currentPageIndex * pageSize + indexOnCurrentPage == totalRowCount;
        return onLastPage && afterLastEntity;
    }

    private void outOfBoundsNext() {
        if (turnAroundMode) {
            // Position pointer on first entity on first page (we're coming from the bottom of last page)
            selectedEntityTotalIndex = 0;
            gotoPage(0);
        } else {
            gotoPage(pageCount - 1);
            // Position pointer on last entity on last page
            indexOnCurrentPage = 0; // TODO 20
            selectedEntityTotalIndex = (int) totalRowCount - 1;
        }
    }

    public void gotoNextPage() {
        offset += pageSize;
        // Out of bounds...
        if (offset > totalRowCount) {
            outOfBoundsNext();
        } else {
            gotoPage(offset / pageSize);
            pointToFirstEntityOnCurrentPage();
        }
    }

    public T next() {
        indexOnCurrentPage++;
        // Check page: go to next page?
        if (indexOnCurrentPage == pageSize || pointerIsAtLastEntity()) {
            gotoNextPage();
        } else { // Move within page
            selectedEntityTotalIndex++;
            // Out of bounds...
            if (selectedEntityTotalIndex > totalRowCount) {
                outOfBoundsNext();
            }
        }
        selectedEntity = currentPage.get(indexOnCurrentPage);
        return selectedEntity;
    }

    public boolean hasPreviousPage() {
        return currentPageIndex > 0;
    }

    public void gotoPreviousPage() {
        offset -= pageSize;
        // Out of bounds...
        if (offset < 0) {
            //offset = 0;
            outOfBoundsPrevious();
        } else {
            gotoPage(offset / pageSize);
            pointToLastEntityOnCurrentPage();
        }
    }

    public T previous() {
        indexOnCurrentPage--;
        // Check page: go to previous page 
        if (indexOnCurrentPage < 0) {
            gotoPreviousPage();
        } else { // Move within page
            selectedEntityTotalIndex--;
            // Out of bounds...
            outOfBoundsPrevious();
        }
        selectedEntity = currentPage.get(indexOnCurrentPage);
        return selectedEntity;
    }

    private void outOfBoundsPrevious() {
        if (selectedEntityTotalIndex < 0) {
            if (turnAroundMode) {
                pointToLastEntityOnLastPage();
            } else {
                pointToFirstEntityOnFirstPage();
            }
        }
    }
    //</editor-fold>

}
