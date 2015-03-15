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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class AktuelleBestellkarte implements GenericEntity, SqlStatementCapable {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Version
    private Long version;

    @Id
    @Column
    @Size(min = 1, max = 5)
    @NotNull
    private String hoerernummer;

    // TODO List<Book>
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> titelnummer;

    @Basic
    @Column
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date datumStand;

    @Override
    public Long getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public List<String> getTitelnummer() {
        if (null == titelnummer) {
            titelnummer = new LinkedList<>();
        }
        return titelnummer;
    }

    public void setTitelnummer(List<String> titelnummern) {
        this.titelnummer = titelnummern;
    }

    public void addTitelnummer(int index, String titelnummer) {
        getTitelnummer()
                .add(index, titelnummer);
    }

    @Override
    public String toInsertStatement() {
        String s = "null";
        if (null != datumStand) {
            s = String.format("'%s'", simpleDateFormat.format(datumStand));
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final int titelnummernMaxIndex = titelnummer.size() - 1;
        for (int idx = 0; idx < titelnummer.size(); idx++) {
            final String t = titelnummer.get(idx);
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
