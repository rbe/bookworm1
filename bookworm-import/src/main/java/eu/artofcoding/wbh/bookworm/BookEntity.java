package eu.artofcoding.wbh.bookworm;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class BookEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String einstelldatum; // .80

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
        return spieldauer;
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

    public String getEinstelldatum() {
        return einstelldatum;
    }

    public void setEinstelldatum(String einstelldatum) {
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
/*
    Jetzt kommen noch die einzelnen Sachgebiete aufgeschlüsselt:
    A Klassiker der Weltliteratur
    B Erzählungen oder Novellen, Kurzgeschichten, Märchen und Sagen
    C Lyrik und Anthologien.
    D Literatur der Moderne und Problemliteratur
    E Unterhaltungsliteratur, wie z.B. Liebes- und Schicksalsromane.
    F Historische Romane.
    G Kriminal- und Agentenromane, Kriegserlebnisse und Abenteuererzählungen.
    H Humor und Satire
    I Science Fiction-Romane und phantastische Literatur
    J Biographien, Tagebücher und Briefe
    K Abhandlungen über Literatur, Musik und Kunst.
    L Philosophie, Psychologie, Religion, Theologie und sonstige religiöse
    L iteratur, auch die der nichtchristlichen Religionen
    M Reiseführer, Reise-, Länder- und Städtebeschreibungen
    N Kulturgeschichte, Zeitgeschichte sowie Bücher über Archäologie.
    O Politik, Gesellschaft und Pädagogik
    P Wirtschaft und Recht.
    Q Naturwissenschaft, Medizin und Technik.
    R Tiergeschichten, Tierverhalten
    S Kinder- und Jugendliteratur
    T Hobbies (z.B. Kochbücher), praktische Bücher, Ratgeber, Weiterbildung
    U Einige Titel in englischer und französischer Sprache,
    V Blindenwesen
    W Hörspiele, die von verschiedenen Rundfunkanstalten übernommen werden konnten, und Dramen
    X Stimme des Autors bzw. spezieller Rezitatoren
    */
}
