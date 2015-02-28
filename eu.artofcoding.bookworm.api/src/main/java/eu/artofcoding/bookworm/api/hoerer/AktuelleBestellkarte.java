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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class AktuelleBestellkarte implements SqlStatementCapable {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    @Column
    private String hoerernummer;

    @ElementCollection
    private List<String> titelnummern = new LinkedList<>();

    @Basic
    @Temporal(value = TemporalType.DATE)
    private Date datumStand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatumStand() {
        return datumStand;
    }

    public void setDatumStand(Date datumStand) {
        this.datumStand = datumStand;
    }

    public String getHoerernummer() {
        return hoerernummer;
    }

    public void setHoerernummer(String hoerernummer) {
        this.hoerernummer = hoerernummer;
    }

    public List<String> getTitelnummern() {
        return titelnummern;
    }

    public void setTitelnummern(List<String> titelnummern) {
        this.titelnummern = titelnummern;
    }

    public void addTitelnummer(int index, String titelnummer) {
        titelnummern.add(index, titelnummer);
    }

    @Override
    public String toInsertStatement() {
        String s = "null";
        if (null != datumStand) {
            s = String.format("'%s'", simpleDateFormat.format(datumStand));
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final int titelnummernMaxIndex = titelnummern.size() - 1;
        for (int idx = 0; idx < titelnummern.size(); idx++) {
            final String t = titelnummern.get(idx);
            final boolean hasContent = null != t && !t.isEmpty();
            if (hasContent) {
                final boolean idxIsWithinRange = idx > 0 && idx < titelnummernMaxIndex;
                if (idxIsWithinRange) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(t);
            } else {
                break;
            }
        }
        return String.format("INSERT INTO hoererstamm_aktuelle_bestellkarte" +
                " (hoerernummer, titelnummern, datumStand)" +
                " VALUES ('%s', '%s', %s);", hoerernummer, stringBuilder.toString(), s);
    }

}
