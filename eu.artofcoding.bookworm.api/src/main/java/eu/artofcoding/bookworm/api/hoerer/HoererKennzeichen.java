/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api.hoerer;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;
import eu.artofcoding.bookworm.api.SqlStatementCapable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class HoererKennzeichen implements GenericEntity, SqlStatementCapable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    @NotNull
    @Size(min = 1, max = 5)
    private String hoerernummer;

    @Basic
    @Column
    @Size(min = 0, max = 80)
    private String email;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

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