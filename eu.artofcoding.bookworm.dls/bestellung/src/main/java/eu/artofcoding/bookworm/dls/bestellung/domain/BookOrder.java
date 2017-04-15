/*
 * eu.artofcoding.bookworm
 *
 * Copyright (C) 2011-2017 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.dls.bestellung.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class BookOrder {

    private static final long serialVersionUID = -1L;

    private String abrufkennwort;

    private String billetStatus;

    public BookOrder() {
    }

    BookOrder(final String abrufkennwort, final String billetStatus) {
        this.abrufkennwort = abrufkennwort;
        this.billetStatus = billetStatus;
    }

    @XmlElement(name = "Abrufkennwort")
    public String getAbrufkennwort() {
        return abrufkennwort;
    }

    void setAbrufkennwort(final String abrufkennwort) {
        this.abrufkennwort = abrufkennwort;
    }

    @XmlElement(name = "BilletStatus")
    public String getBilletStatus() {
        return billetStatus;
    }

    void setBilletStatus(final String billetStatus) {
        this.billetStatus = billetStatus;
    }

    @Override
    public String toString() {
        return "BookOrder{" +
                "abrufkennwort='" + abrufkennwort + '\'' +
                ", billetStatus='" + billetStatus + '\'' +
                '}';
    }

}
