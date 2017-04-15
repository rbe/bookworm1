/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
})
package eu.artofcoding.bookworm.dls.lieferung.integration;

import eu.artofcoding.bookworm.common.helper.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;
