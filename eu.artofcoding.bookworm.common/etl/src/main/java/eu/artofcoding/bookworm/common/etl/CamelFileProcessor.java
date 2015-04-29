/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.etl;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import java.util.List;

public interface CamelFileProcessor {

    List<GenericEntity> importFile(String body) throws Exception;

}
