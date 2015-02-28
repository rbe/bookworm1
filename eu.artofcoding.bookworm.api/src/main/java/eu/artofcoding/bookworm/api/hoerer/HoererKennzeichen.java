/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.hoerer;

import eu.artofcoding.bookworm.api.persistence.SqlStatementCapable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HoererKennzeichen implements SqlStatementCapable {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    @Column
    private String hoerernummer;

    @Basic
    @Column
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    @Override
    public String toInsertStatement() {
        return String.format("UPDATE hoerer" +
                " SET email = '%s'" +
                " WHERE hoerernummer = '%s';", email, hoerernummer);
    }

}
