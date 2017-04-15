/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.lieferung.integration;

import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.InputStream;

public class DlsBookResponseTest {

    @Test
    public void shouldUnmarshalBookStatusResponse() {
        final InputStream resourceAsStream = DlsBookResponseTest.class.getResourceAsStream("/dls/DlsBookStatusResponse.xml");
        final DlsBookResponse unmarshal = JAXB.unmarshal(resourceAsStream, DlsBookResponse.class);
        System.out.println(unmarshal);
    }

}
