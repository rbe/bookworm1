/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.view;

import eu.artofcoding.bookworm.common.helper.ParserHelper;
import eu.artofcoding.bookworm.common.persistence.hoerer.BestellkarteArchiv;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class HoererBucharchiv extends AbstractHoererBean {

    private List<BestellkarteArchiv> bestellkarteArchiv;

    public boolean hasBestellkartenArchiv() {
        bestellkarteArchiv = getBestellkartenArchiv();
        return null != bestellkarteArchiv && bestellkarteArchiv.size() > 0;
    }

    public List<BestellkarteArchiv> getBestellkartenArchiv() {
        if (null == bestellkarteArchiv) {
            bestellkarteArchiv = hoererSession.getBestellkarteArchiv();
        }
        return bestellkarteArchiv;
    }

    public void search(ActionEvent e) {
        final String titel = String.format("%%%s%%", searchTitle);
        Date datum = null;
        if (null != searchDate && searchDate.length() == 10) {
            datum = ParserHelper.parseGermanDate(searchDate);
        }
        bestellkarteArchiv = hoererSession.findBestellkarteArchivByTitelOrDatum(titel, datum);
    }

}
