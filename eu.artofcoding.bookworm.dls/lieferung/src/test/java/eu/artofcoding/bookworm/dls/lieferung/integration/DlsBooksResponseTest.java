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

public class DlsBooksResponseTest {

    @Test
    public void shouldUnmarshalBooksStatusResponse() {
        final InputStream resourceAsStream = DlsBooksResponseTest.class.getResourceAsStream("/dls/DlsBooksStatusResponse.xml");
        final DlsBooksResponse unmarshal = JAXB.unmarshal(resourceAsStream, DlsBooksResponse.class);
        System.out.println(unmarshal);
    }

}
