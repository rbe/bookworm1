/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.datimport;

import eu.artofcoding.bookworm.api.CamelFileProcessor;
import eu.artofcoding.bookworm.api.book.Book;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;

public class BookImportProcessor implements CamelFileProcessor {

    @PersistenceContext
    private EntityManager em;

    private BookFileParser bookFileParser;

    public void setBookFileParser(final BookFileParser bookFileParser) {
        this.bookFileParser = bookFileParser;
    }

    @Transactional
    public void importFile(final File file) throws Exception {
        // Check state
        if (null == bookFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Truncate table
        em.createNativeQuery("TRUNCATE TABLE books").executeUpdate();
        // Insert data
        final List<Book> bookEntities = bookFileParser.parseFile(file.toURI());
        for (Book b : bookEntities) {
            em.persist(b);
        }
    }

}