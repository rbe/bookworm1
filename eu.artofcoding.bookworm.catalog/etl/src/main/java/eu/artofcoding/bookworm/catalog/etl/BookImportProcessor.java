/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.catalog.etl;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.common.etl.CamelFileProcessor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BookImportProcessor implements CamelFileProcessor {

    @PersistenceContext
    private EntityManager em;

    private BookFileParser bookFileParser;

    public void setBookFileParser(final BookFileParser bookFileParser) {
        this.bookFileParser = bookFileParser;
    }

    @Transactional
    public List<GenericEntity> importFile(final String body) throws Exception {
        // Check state
        if (null == bookFileParser || null == em) {
            throw new IllegalStateException("No file parser or no entity manager");
        }
        // Insert data
        final List<GenericEntity> genericEntities = bookFileParser.parse(body);
        for (GenericEntity b : genericEntities) {
            em.persist(b);
        }
        return genericEntities;
    }

}
