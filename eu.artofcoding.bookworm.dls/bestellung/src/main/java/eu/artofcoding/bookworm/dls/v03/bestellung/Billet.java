package eu.artofcoding.bookworm.dls.v03.bestellung;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Billet {

    @XmlElement(name = "UserID")
    private String userId;

    @XmlElement(name = "BibliothekID")
    private String bibliothekId = "wbh06";

    @XmlElement(name = "Bestellnummer")
    private String bestellnummer;

    @XmlElement(name = "Abrufkennwort")
    private String abrufkennwort;

    public Billet() {
    }

    public Billet(final String userId, final String bestellnummer, final String abrufkennwort) {
        this.userId = userId;
        this.bestellnummer = bestellnummer;
        this.abrufkennwort = abrufkennwort;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getBibliothekId() {
        return bibliothekId;
    }

    public void setBibliothekId(final String bibliothekId) {
        this.bibliothekId = bibliothekId;
    }

    public String getBestellnummer() {
        return bestellnummer;
    }

    public void setBestellnummer(final String bestellnummer) {
        this.bestellnummer = bestellnummer;
    }

    public String getAbrufkennwort() {
        return abrufkennwort;
    }

    public void setAbrufkennwort(final String abrufkennwort) {
        this.abrufkennwort = abrufkennwort;
    }

    @Override
    public String toString() {
        return String.format("Billet{userId='%s', bestellnummer='%s', abrufkennwort='%s'}", userId, bestellnummer, abrufkennwort);
    }

}
