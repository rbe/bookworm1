/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.hoerer.Belastung;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class HoererBuchausleihe extends AbstractHoererBean {

    private List<Belastung> belastungen;

    public List<Belastung> getBelastungen() {
        if (null == belastungen) {
            belastungen = hoererSession.getMyData().getBelastungen();
        }
        return belastungen;
    }

    public void search(ActionEvent e) {
        final String titel = String.format("%%%s%%", searchTitle);
        Date datum = null;
        if (null != searchDate && searchDate.length() == 10) {
            datum = ParserHelper.parseGermanDate(searchDate);
        }
        belastungen = hoererSession.getMyData().findBelastungenBooksByTitleOrDatum(titel, datum);
    }

}
