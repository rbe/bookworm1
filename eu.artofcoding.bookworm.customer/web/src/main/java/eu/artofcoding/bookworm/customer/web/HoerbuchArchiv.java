/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web;

import eu.artofcoding.bookworm.api.hoerer.BestellkarteArchiv;
import eu.artofcoding.bookworm.api.hoerer.HoererBuchstamm;
import eu.artofcoding.bookworm.api.hoerer.HoererKennzeichen;
import eu.artofcoding.bookworm.api.hoerer.Hoererstamm;
import eu.artofcoding.bookworm.customer.web.jsf.FacesHelper;
import eu.artofcoding.bookworm.customer.web.persistence.BestellkarteArchivDAO;
import eu.artofcoding.bookworm.customer.web.persistence.HoererBuchstammDAO;
import eu.artofcoding.bookworm.customer.web.persistence.HoererKennzeichenDAO;
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
public class HoerbuchArchiv implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(HoerbuchArchiv.class.getName());

    //<editor-fold desc="Member">

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private HoererstammDAO hoererstammDAO;

    @EJB
    private HoererBuchstammDAO hoererBuchstammDAO;

    @EJB
    private HoererKennzeichenDAO hoererKennzeichenDAO;

    @EJB
    private BestellkarteArchivDAO bestellkarteArchivDAO;

    private Hoererstamm hoerer;

    private HoererBuchstamm hoererBuch;

    private HoererKennzeichen hoererKennzeichen;

    private BestellkarteArchiv bestellkarteArchiv;

    //</editor-fold>

    @PostConstruct
    public void initialize() {
        hoererstammDAO.setEntityManager(entityManager);
        final String hoerernummer = FacesHelper.getRequestValue("hnr");
        hoerer = hoererstammDAO.findByHoerernummer(hoerernummer);
        hoererBuchstammDAO.setEntityManager(entityManager);
        hoererBuch = hoererBuchstammDAO.findByHoerernummer(hoerernummer);
        hoererKennzeichenDAO.setEntityManager(entityManager);
        hoererKennzeichen = hoererKennzeichenDAO.findByHoerernummer(hoerernummer);
        bestellkarteArchivDAO.setEntityManager(entityManager);
        bestellkarteArchiv = bestellkarteArchivDAO.findByHoerernummer(hoerernummer);
    }

    public Hoererstamm getHoerer() {
        return hoerer;
    }

    public HoererBuchstamm getHoererBuch() {
        return hoererBuch;
    }

    public HoererKennzeichen getHoererKennzeichen() {
        return hoererKennzeichen;
    }

    public BestellkarteArchiv getBestellkarteArchiv() {
        return bestellkarteArchiv;
    }

}
