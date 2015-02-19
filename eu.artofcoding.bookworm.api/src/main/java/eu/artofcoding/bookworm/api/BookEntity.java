/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.api;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NamedQueries({
        //@NamedQuery(name = "findByStichwort", query = "SELECT o FROM BookEntity o WHERE o.autor LIKE :autor OR o.titel LIKE :titel OR o.untertitel LIKE :untertitel OR o.titelnummer = :titelnummer"),
        @NamedQuery(name = "findByAutor", query = "SELECT o FROM BookEntity o WHERE o.autor LIKE :autor"),
        @NamedQuery(name = "findByTitel", query = "SELECT o FROM BookEntity o WHERE o.titel LIKE :titel"),
        @NamedQuery(name = "findByDatum", query = "SELECT o FROM BookEntity o WHERE o.einstelldatum >= :einstelldatum")
})
public class BookEntity implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Version
    public Long version;

    private String sachgebiet;

    private String titelnummer; // 6 0 (nummerisch)

    private String autor; // 79

    private String titel; // 120

    private String untertitel; // 120

    @Column(length = 400)
    private String erlaeuterung; // 400

    private String verlagsort; // 20

    private String verlag; // 30

    private String druckjahr; // 40

    private String sprecher1; // 150

    private String sprecher2; // 250

    private String spieldauer; // 52 Stunde, Minuten

    private String prodOrt; // 20

    private String prodJahr; // 40

    private String suchwoerter; // 80

    private String anzahlCD; // 20

    private String titelfamilie; // 40 wird nicht gebraucht

    private java.sql.Date einstelldatum; // .80

    public static final List<SachgebietEntity> SACHGEBIET_ENTITY = new ArrayList<SachgebietEntity>();

    static {
        SACHGEBIET_ENTITY.add(new SachgebietEntity("A", "Klassiker der Weltliteratur"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("B", "Erzählungen oder Novellen, Kurzgeschichten, Märchen und Sagen"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("C", "Lyrik und Anthologien"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("D", "Literatur der Moderne und Problemliteratur"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("E", "Unterhaltungsliteratur – Schicksalsromane – Liebesromane"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("F", "Historische Romane"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("G", "Kriminal-, Agenten-, Abenteuerromane, Kriegserlebnisse, Western"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("H", "Humor und Satire"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("I", "Science Fiction - Phantastische Literatur"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("J", "Biographien – Erinnerungen – Tagebücher – Briefe"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("K", "Literatur – Musik – Kunst"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("L", "Philosophie – Psychologie – Religion – Religiöse Literatur"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("M", "Städte – Länder – Völker – Reisen – Expeditionen"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("N", "Geschichte – Zeitgeschichte – Kulturgeschichte – Archäologie"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("O", "Politik - Gesellschaft - Pädagogik"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("P", "Recht - Wirtschaft"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("Q", "Naturwissenschaft - Medizin - Technik"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("R", "Tiergeschichten - Tierverhalten"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("S", "Kinder- und Jugendbücher"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("T", "Hobbys- Praktische Bücher – Ratgeber – Weiterbildung"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("U", "Fremdsprachige Bücher"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("V", "Blindenwesen"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("W", "Hörspiele – Dramen"));
        SACHGEBIET_ENTITY.add(new SachgebietEntity("X", "Stimme des Autors"));
    }

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

    public String getSachgebiet() {
        if (null != sachgebiet) {
            for (SachgebietEntity s : SACHGEBIET_ENTITY) {
                if (sachgebiet.equals(s.getName())) {
                    return s.getDescription();
                }
            }
        }
        return sachgebiet;
    }

    public void setSachgebiet(String sachgebiet) {
        this.sachgebiet = sachgebiet;
    }

    public String getTitelnummer() {
        return titelnummer;
    }

    public void setTitelnummer(String titelnummer) {
        this.titelnummer = titelnummer;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public String getErlaeuterung() {
        return erlaeuterung;
    }

    public void setErlaeuterung(String erlaeuterung) {
        this.erlaeuterung = erlaeuterung;
    }

    public String getVerlagsort() {
        return verlagsort;
    }

    public void setVerlagsort(String verlagsort) {
        this.verlagsort = verlagsort;
    }

    public String getVerlag() {
        return verlag;
    }

    public void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    public String getDruckjahr() {
        return druckjahr;
    }

    public void setDruckjahr(String druckjahr) {
        this.druckjahr = druckjahr;
    }

    public String getSprecher1() {
        return sprecher1;
    }

    public void setSprecher1(String sprecher1) {
        this.sprecher1 = sprecher1;
    }

    public String getSprecher2() {
        return sprecher2;
    }

    public void setSprecher2(String sprecher2) {
        this.sprecher2 = sprecher2;
    }

    public String getSpieldauer() {
        String _spieldauer = null;
        if (null != spieldauer) {
            String[] parts = spieldauer.split(",");
            if (!parts[1].equals("00")) {
                _spieldauer = String.format("%s Stunden %s Minuten", parts[0], parts[1]);
            } else {
                _spieldauer = String.format("%s Stunden", parts[0]);
            }
        }
        return _spieldauer;
    }

    public void setSpieldauer(String spieldauer) {
        this.spieldauer = spieldauer;
    }

    public String getProdOrt() {
        return prodOrt;
    }

    public void setProdOrt(String prodOrt) {
        this.prodOrt = prodOrt;
    }

    public String getProdJahr() {
        return prodJahr;
    }

    public void setProdJahr(String prodJahr) {
        this.prodJahr = prodJahr;
    }

    public String getSuchwoerter() {
        return suchwoerter;
    }

    public void setSuchwoerter(String suchwoerter) {
        this.suchwoerter = suchwoerter;
    }

    public String getAnzahlCD() {
        return anzahlCD;
    }

    public void setAnzahlCD(String anzahlCD) {
        this.anzahlCD = anzahlCD;
    }

    public String getTitelfamilie() {
        return titelfamilie;
    }

    public void setTitelfamilie(String titelfamilie) {
        this.titelfamilie = titelfamilie;
    }

    public java.sql.Date getEinstelldatum() {
        return einstelldatum;
    }

    public void setEinstelldatum(java.sql.Date einstelldatum) {
        this.einstelldatum = einstelldatum;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + id + '\'' +
                ", sachgebiet='" + sachgebiet + '\'' +
                ", titelnummer='" + titelnummer + '\'' +
                ", autor='" + autor + '\'' +
                ", titel='" + titel + '\'' +
                ", untertitel='" + untertitel + '\'' +
                ", erlaeuterung='" + erlaeuterung + '\'' +
                ", verlagsort='" + verlagsort + '\'' +
                ", verlag='" + verlag + '\'' +
                ", druckjahr='" + druckjahr + '\'' +
                ", sprecher1='" + sprecher1 + '\'' +
                ", sprecher2='" + sprecher2 + '\'' +
                ", spieldauer='" + spieldauer + '\'' +
                ", prodOrt='" + prodOrt + '\'' +
                ", prodJahr='" + prodJahr + '\'' +
                ", suchwoerter='" + suchwoerter + '\'' +
                ", anzahlCD='" + anzahlCD + '\'' +
                ", titelfamilie='" + titelfamilie + '\'' +
                ", einstelldatum='" + einstelldatum + '\'' +
                '}';
    }

}
