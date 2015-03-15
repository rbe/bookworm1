/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.api.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.jsf.FacesHelper;
import eu.artofcoding.bookworm.customer.web.persistence.BookDAO;
import eu.artofcoding.bookworm.customer.web.persistence.HoererBuchstammDAO;
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
public class HoerbuchAusleihe implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(HoerbuchAusleihe.class.getName());

    //<editor-fold desc="Member">

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private HoererstammDAO hoererstammDAO;

    @EJB
    private HoererBuchstammDAO hoererBuchstammDAO;

    @EJB
    private BookDAO bookDAO;

    private Hoererstamm hoerer;

    private HoererBuchstamm hoererBuchstamm;

    //</editor-fold>

    @PostConstruct
    public void initialize() {
        hoererstammDAO.setEntityManager(entityManager);
        final String hoerernummer = FacesHelper.getRequestValue("hnr");
        hoerer = hoererstammDAO.findByHoerernummer(hoerernummer);
        hoererBuchstammDAO.setEntityManager(entityManager);
        hoererBuchstamm = hoererBuchstammDAO.findByHoerernummer(hoerernummer);
        bookDAO.setEntityManager(entityManager);
    }

    public Hoererstamm getHoerer() {
        return hoerer;
    }

    public HoererBuchstamm getHoererBuchstamm() {
        return hoererBuchstamm;
    }

}
