/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.etl.xml;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import java.util.List;

public interface XmlRowProcessor<T extends GenericEntity> {

    T xmlRowToEntity(XmlRow xmlRow);

    T validateAndMerge(final T entity);

    List<T> validateAndMerge(final List<T> entities);

}
