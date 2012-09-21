/*
 * bookworm
 * bookworm-import
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu/
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 27.08.12 10:23
 */

package eu.artofcoding.wbh.bookworm;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;

@Transactional
public class BookwormImportProcessor {

    @PersistenceContext
    private EntityManager em;

    private BookwormFileParser bookwormFileParser;

    public void setBookwormFileParser(BookwormFileParser bookwormFileParser) {
        this.bookwormFileParser = bookwormFileParser;
    }

    public void importFile(File file) throws Exception {
        // Check state
        if (null == bookwormFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Truncate table
        em.createNativeQuery("TRUNCATE TABLE books").executeUpdate();
        // Insert data
        List<BookEntity> bookEntities = bookwormFileParser.parseFile(file.toURI());
        for (BookEntity b : bookEntities) {
            em.persist(b);
        }
    }

}
