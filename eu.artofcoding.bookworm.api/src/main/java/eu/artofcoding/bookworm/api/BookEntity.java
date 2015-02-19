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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final List<Sachgebiet> SACHGEBIET = new ArrayList<Sachgebiet>();

    static {
        SACHGEBIET.add(new Sachgebiet("A", "Klassiker der Weltliteratur"));
        SACHGEBIET.add(new Sachgebiet("B", "Erzählungen oder Novellen, Kurzgeschichten, Märchen und Sagen"));
        SACHGEBIET.add(new Sachgebiet("C", "Lyrik und Anthologien"));
        SACHGEBIET.add(new Sachgebiet("D", "Literatur der Moderne und Problemliteratur"));
        SACHGEBIET.add(new Sachgebiet("E", "Unterhaltungsliteratur – Schicksalsromane – Liebesromane"));
        SACHGEBIET.add(new Sachgebiet("F", "Historische Romane"));
        SACHGEBIET.add(new Sachgebiet("G", "Kriminal-, Agenten-, Abenteuerromane, Kriegserlebnisse, Western"));
        SACHGEBIET.add(new Sachgebiet("H", "Humor und Satire"));
        SACHGEBIET.add(new Sachgebiet("I", "Science Fiction - Phantastische Literatur"));
        SACHGEBIET.add(new Sachgebiet("J", "Biographien – Erinnerungen – Tagebücher – Briefe"));
        SACHGEBIET.add(new Sachgebiet("K", "Literatur – Musik – Kunst"));
        SACHGEBIET.add(new Sachgebiet("L", "Philosophie – Psychologie – Religion – Religiöse Literatur"));
        SACHGEBIET.add(new Sachgebiet("M", "Städte – Länder – Völker – Reisen – Expeditionen"));
        SACHGEBIET.add(new Sachgebiet("N", "Geschichte – Zeitgeschichte – Kulturgeschichte – Archäologie"));
        SACHGEBIET.add(new Sachgebiet("O", "Politik - Gesellschaft - Pädagogik"));
        SACHGEBIET.add(new Sachgebiet("P", "Recht - Wirtschaft"));
        SACHGEBIET.add(new Sachgebiet("Q", "Naturwissenschaft - Medizin - Technik"));
        SACHGEBIET.add(new Sachgebiet("R", "Tiergeschichten - Tierverhalten"));
        SACHGEBIET.add(new Sachgebiet("S", "Kinder- und Jugendbücher"));
        SACHGEBIET.add(new Sachgebiet("T", "Hobbys- Praktische Bücher – Ratgeber – Weiterbildung"));
        SACHGEBIET.add(new Sachgebiet("U", "Fremdsprachige Bücher"));
        SACHGEBIET.add(new Sachgebiet("V", "Blindenwesen"));
        SACHGEBIET.add(new Sachgebiet("W", "Hörspiele – Dramen"));
        SACHGEBIET.add(new Sachgebiet("X", "Stimme des Autors"));
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
            for (Sachgebiet s : SACHGEBIET) {
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
