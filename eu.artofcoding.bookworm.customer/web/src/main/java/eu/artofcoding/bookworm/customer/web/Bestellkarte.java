/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.api.hoerer.AktuelleBestellkarte;
import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.jsf.FacesHelper;
import eu.artofcoding.bookworm.customer.web.persistence.AktuelleBestellkarteDAO;
import eu.artofcoding.bookworm.customer.web.persistence.HoererstammDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class Bestellkarte implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(Bestellkarte.class.getName());

    //<editor-fold desc="Member">

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private HoererstammDAO hoererstammDAO;

    @EJB
    private AktuelleBestellkarteDAO aktuelleBestellkarteDAO;

    private Hoererstamm hoerer;

    private AktuelleBestellkarte aktuelleBestellkarte;

    //</editor-fold>

    @PostConstruct
    public void initialize() {
        hoererstammDAO.setEntityManager(entityManager);
        final String hoerernummer = FacesHelper.getRequestValue("hnr");
        hoerer = hoererstammDAO.findByHoerernummer(hoerernummer);
        aktuelleBestellkarteDAO.setEntityManager(entityManager);
        aktuelleBestellkarte = aktuelleBestellkarteDAO.findByHoerernummer(hoerernummer);
    }

    public Hoererstamm getHoerer() {
        return hoerer;
    }

    public AktuelleBestellkarte getAktuelleBestellkarte() {
        return aktuelleBestellkarte;
    }

}
