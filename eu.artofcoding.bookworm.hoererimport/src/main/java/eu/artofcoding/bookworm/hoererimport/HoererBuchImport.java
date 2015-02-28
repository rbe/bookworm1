/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */
package eu.artofcoding.bookworm.hoererimport;

import eu.artofcoding.bookworm.hoererimport.as400xml.BkrxstpImport;

public class HoererBuchImport {

    public static void main(String[] args) throws Exception {
        /*HoerstpImport.hoerstpToSql();
        HoekzstpImport.hoekzstpToSql();
        BkstpImport.bkstpToSql();*/
        BkrxstpImport.bkrxstpToSql();
    }

}
