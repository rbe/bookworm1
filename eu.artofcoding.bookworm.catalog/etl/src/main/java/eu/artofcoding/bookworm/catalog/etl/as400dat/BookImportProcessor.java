/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl.as400dat;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.etl.CamelFileProcessor;
import eu.artofcoding.bookworm.common.persistence.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

public class BookImportProcessor implements CamelFileProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookImportProcessor.class);

    @PersistenceContext
    private EntityManager em;

    private BookFileParser bookFileParser;

    private int bookCount = 0;

    private boolean mergeOccured;

    public void setBookFileParser(final BookFileParser bookFileParser) {
        this.bookFileParser = bookFileParser;
    }

    private void bookToDb(final Book book) {
        try {
            final Book b = em.find(Book.class, book.getTitelnummer());
            if (null != b) {
                LOGGER.debug("Merging {} with {}", b, book);
                em.merge(book);
                mergeOccured = true;
            } else {
                LOGGER.info("Inserting {}", book);
                em.persist(book);
            }
            if (LOGGER.isDebugEnabled() && bookCount % 1000 == 0) {
                LOGGER.debug("Processed book #{}", bookCount);
            }
            bookCount++;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    /**
     * Delete all books in database but not in import list.
     */
    private void removeBooks(final List<GenericEntity> genericEntities) {
        LOGGER.info("Looking for books to remove");
        final TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        final List<Book> resultList = query.getResultList();
        for (Book book : resultList) {
            final boolean anyMatch = genericEntities.parallelStream().anyMatch(b -> ((Book) b).getTitelnummer().equals(book.getTitelnummer()));
            if (!anyMatch) {
                LOGGER.info("Removing Book{titelnummer={}}", book.getTitelnummer());
                em.remove(book);
            }
        }
    }

    /**
     * Merge or insert data.
     */
    private List<GenericEntity> mergeOrInsertBooks(final String body) throws IOException {
        final List<GenericEntity> genericEntities = bookFileParser.parse(body);
        for (GenericEntity genericEntity : genericEntities) {
            final Book book = (Book) genericEntity;
            bookToDb(book);
        }
        LOGGER.info("Merged/inserted {} books", genericEntities.size());
        return genericEntities;
    }

    @Transactional
    public List<GenericEntity> importFile(final String body) throws Exception {
        // Check state
        if (null == bookFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        final List<GenericEntity> genericEntities = mergeOrInsertBooks(body);
        if (mergeOccured) {
            removeBooks(genericEntities);
        }
        return genericEntities;
    }

}
