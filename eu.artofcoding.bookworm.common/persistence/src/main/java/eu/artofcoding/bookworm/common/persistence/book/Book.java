/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.persistence.book;

import eu.artofcoding.beetlejuice.api.persistence.GenericEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        //@NamedQuery(name = "findByStichwort", query = "SELECT o FROM Book o WHERE o.autor LIKE :autor OR o.titel LIKE :titel OR o.untertitel LIKE :untertitel OR o.titelnummer = :titelnummer"),
        @NamedQuery(name = "Book.findByTitelnummer", query = "SELECT o FROM Book o WHERE o.titelnummer = :titelnummer"),
        @NamedQuery(name = "findByAutor", query = "SELECT o FROM Book o WHERE o.autor LIKE :autor"),
        @NamedQuery(name = "findByTitel", query = "SELECT o FROM Book o WHERE o.titel LIKE :titel"),
        @NamedQuery(name = "findByDatum", query = "SELECT o FROM Book o WHERE o.einstelldatum >= :einstelldatum")
})
public class Book implements GenericEntity {

    public static final List<Sachgebiet> SACHGEBIET_ENTITY = new ArrayList<>();

    static {
        SACHGEBIET_ENTITY.add(new Sachgebiet("A", "Klassiker der Weltliteratur"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("B", "Erzählungen oder Novellen, Kurzgeschichten, Märchen und Sagen"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("C", "Lyrik und Anthologien"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("D", "Literatur der Moderne und Problemliteratur"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("E", "Unterhaltungsliteratur – Schicksalsromane – Liebesromane"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("F", "Historische Romane"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("G", "Kriminal-, Agenten-, Abenteuerromane, Kriegserlebnisse, Western"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("H", "Humor und Satire"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("I", "Science Fiction - Phantastische Literatur"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("J", "Biographien – Erinnerungen – Tagebücher – Briefe"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("K", "Literatur – Musik – Kunst"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("L", "Philosophie – Psychologie – Religion – Religiöse Literatur"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("M", "Städte – Länder – Völker – Reisen – Expeditionen"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("N", "Geschichte – Zeitgeschichte – Kulturgeschichte – Archäologie"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("O", "Politik - Gesellschaft - Pädagogik"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("P", "Recht - Wirtschaft"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("Q", "Naturwissenschaft - Medizin - Technik"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("R", "Tiergeschichten - Tierverhalten"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("S", "Kinder- und Jugendbücher"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("T", "Hobbys- Praktische Bücher – Ratgeber – Weiterbildung"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("U", "Fremdsprachige Bücher"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("V", "Blindenwesen"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("W", "Hörspiele – Dramen"));
        SACHGEBIET_ENTITY.add(new Sachgebiet("X", "Stimme des Autors"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Version
    public Long version;

    @Basic
    @Column
    private String sachgebiet;

    @Basic
    @Column(unique = true)
    private String titelnummer; // 6 0 (nummerisch)

    @Basic
    @Column
    private String autor; // 79

    @Basic
    @Column
    private String titel; // 120

    @Basic
    @Column
    private String untertitel; // 120

    @Basic
    @Column(length = 400)
    private String erlaeuterung; // 400

    @Basic
    @Column
    private String verlagsort; // 20

    @Basic
    @Column
    private String verlag; // 30

    @Basic
    @Column
    private String druckjahr; // 40

    @Basic
    @Column
    private String sprecher1; // 150

    @Basic
    @Column
    private String sprecher2; // 250

    @Basic
    @Column
    private String spieldauer; // 52 Stunde, Minuten

    @Basic
    @Column
    private String prodOrt; // 20

    @Basic
    @Column
    private String prodJahr; // 40

    @Basic
    @Column
    private String suchwoerter; // 80

    @Basic
    @Column
    private String anzahlCD; // 20

    @Basic
    @Column
    private String titelfamilie; // 40 wird nicht gebraucht

    @Basic
    @Column
    private
    Date einstelldatum; // .80

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
            for (Sachgebiet s : SACHGEBIET_ENTITY) {
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

    public Date getEinstelldatum() {
        return einstelldatum;
    }

    public void setEinstelldatum(Date einstelldatum) {
        this.einstelldatum = einstelldatum;
    }

    @Override
    public String toString() {
        return String.format("BookEntity{id='%d', sachgebiet='%s', titelnummer='%s', autor='%s', titel='%s', untertitel='%s', erlaeuterung='%s', verlagsort='%s', verlag='%s', druckjahr='%s', sprecher1='%s', sprecher2='%s', spieldauer='%s', prodOrt='%s', prodJahr='%s', suchwoerter='%s', anzahlCD='%s', titelfamilie='%s', einstelldatum='%s'}", id, sachgebiet, titelnummer, autor, titel, untertitel, erlaeuterung, verlagsort, verlag, druckjahr, sprecher1, sprecher2, spieldauer, prodOrt, prodJahr, suchwoerter, anzahlCD, titelfamilie, einstelldatum);
    }

}
