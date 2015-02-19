/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.bookimport;

import eu.artofcoding.bookworm.api.BookEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;

@Transactional
public class BookImportProcessor {

    @PersistenceContext
    private EntityManager em;

    private BookFileParser bookFileParser;

    public void setBookFileParser(BookFileParser bookFileParser) {
        this.bookFileParser = bookFileParser;
    }

    public void importFile(File file) throws Exception {
        // Check state
        if (null == bookFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Truncate table
        em.createNativeQuery("TRUNCATE TABLE books").executeUpdate();
        // Insert data
        List<BookEntity> bookEntities = bookFileParser.parseFile(file.toURI());
        for (BookEntity b : bookEntities) {
            em.persist(b);
        }
    }

}
