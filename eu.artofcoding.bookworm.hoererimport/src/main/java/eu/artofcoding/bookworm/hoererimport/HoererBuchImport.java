/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */
package eu.artofcoding.bookworm.hoererimport;

import eu.artofcoding.bookworm.hoererimport.bkp.BkstpImport;
import eu.artofcoding.bookworm.hoererimport.hoekz.HoekzstpImport;
import eu.artofcoding.bookworm.hoererimport.hoerstp.HoerstpImport;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;

public class HoererBuchImport {

    public static void main(String[] args) throws XMLStreamException, IOException, URISyntaxException {
        HoerstpImport.hoerstpToSql();
        HoekzstpImport.hoekzstpToSql();
        BkstpImport.bkstpToSql();
    }

}

