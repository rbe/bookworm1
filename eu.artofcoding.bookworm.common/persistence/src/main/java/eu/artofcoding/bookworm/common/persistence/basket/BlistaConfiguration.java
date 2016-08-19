/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.basket;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class BlistaConfiguration implements GenericEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Basic
    @Column
    private int maxDownloadsPerMonth;

    @Basic
    @Column
    private int maxDownloadsPerDay;

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

    public int getMaxDownloadsPerMonth() {
        return maxDownloadsPerMonth;
    }

    public void setMaxDownloadsPerMonth(final int maxDownloadsPerMonth) {
        this.maxDownloadsPerMonth = maxDownloadsPerMonth;
    }

    public int getMaxDownloadsPerDay() {
        return maxDownloadsPerDay;
    }

    public void setMaxDownloadsPerDay(final int maxDownloadsPerDay) {
        this.maxDownloadsPerDay = maxDownloadsPerDay;
    }

}
